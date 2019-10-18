package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import com.mifeng.mf.Common.MFEvent;
import com.mifeng.mf.ControllerLogic.BaseViewControllerLogic;
import com.mifeng.mf.MFNavigation.Interface.IDestroyable;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Utils.StringUtils;
import com.mifeng.mf.MFNavigation.Utils.UiThread;
import com.mifeng.mf.MFNavigation.Views.BehaviourAdapter;

import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.forEach;
import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.perform;

import com.mifeng.mf.MFNavigation.Utils.Functions.Func1;
import com.mifeng.mf.MFNavigation.Views.Component;
import com.mifeng.mf.MFViewAnimator.ViewAnimator;

/**
 * Created by Administrator on 2019/8/19.
 */

public abstract class ViewController<T extends ViewGroup> implements ViewTreeObserver.OnGlobalLayoutListener,
        ViewGroup.OnHierarchyChangeListener,
        BehaviourAdapter<T> {


    @Nullable
    protected T view;
    @Nullable
    private ParentController<T> parentController;
    private final Activity activity;
    private final String id;
    private boolean isDestroyed;
    private final List<Runnable> onAppearedListeners = new ArrayList();
    private boolean isShown;
    private boolean appearEventPosted;
    private boolean isFirstLayout = true;
    private boolean activation = false;

    protected ViewAnimator pushViewAnimator;
    protected ViewAnimator popViewAnimator;

    protected BaseViewControllerLogic baseLogicHander;

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public ViewController(Activity activity, String id) {
        this.activity = activity;
        this.id = id;
    }

    public void addOnAppearedListener(Runnable onAppearedListener) {
        onAppearedListeners.add(onAppearedListener);
    }

    public void removeOnAppearedListener(Runnable onAppearedListener) {
        onAppearedListeners.remove(onAppearedListener);
    }

    protected abstract T createView();

    public Activity getActivity() {
        return activity;
    }

    protected void performOnParentController(Func1<ParentController> task) {
        if (parentController != null) task.run(parentController);
    }

    public ParentController getParentController() {
        return parentController;
    }

    public void setParentController(@NonNull final ParentController parentController) {
        this.parentController = parentController;
    }


    public T getView() {
        if (view == null) {
            if (isDestroyed) {
                throw new RuntimeException("Tried to create view after it has already been destroyed");
            }
            view = createView();
            view.setOnHierarchyChangeListener(this);
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
        return view;
    }


    @CallSuper
    public void onViewAppeared() {
        isShown = true;
        activation = true;
        if (!onAppearedListeners.isEmpty() && !appearEventPosted) {
            appearEventPosted = true;
            UiThread.post(() -> {
                forEach(onAppearedListeners, Runnable::run);
                onAppearedListeners.clear();
            });
        }
        if (baseLogicHander != null) {
            baseLogicHander.activation(true);
        }
    }

    @CallSuper
    public void onViewDisappear() {
        activation = false;
        if (baseLogicHander != null) {
            baseLogicHander.activation(false);
        }
    }

    public void onViewDidLoad() {

    }

    public void onViewDidUnload() {
        if (baseLogicHander != null) {
            baseLogicHander.unRegister();
        }
    }


    protected boolean getActivation() {
        return activation;
    }

    public void onViewWillDisappear() {
    }

    public void onViewWillAppear() {
    }

    public boolean isViewShown() {
        if (this instanceof ChildViewController) {
            return true;
        }
        return !isDestroyed && getView().getVisibility() == View.VISIBLE && view != null;
    }

    public String getId() {
        return id;
    }

    boolean isSameId(final String id) {
        return StringUtils.isEqual(this.id, id);
    }

    @Nullable
    public ViewController findController(String id) {
        return isSameId(id) ? this : null;
    }

    @Nullable
    public ViewController findController(View child) {
        return view == child ? this : null;
    }


    public boolean containsComponent(Component component) {
        return getView().equals(component);
    }


    @CallSuper
    public void destroy() {
        if (isShown) {
            isShown = false;
            if (this instanceof ChildController) {
                Navigator.getInstance().getChildRegistry().onChildDestroyed((ChildController) this);
            }
            onViewDisappear();
            onViewDidUnload();
        }
        if (view instanceof IDestroyable) {
            ((IDestroyable) view).destroy();
        }
        if (view != null) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            view.setOnHierarchyChangeListener(null);
            if (view.getParent() instanceof ViewGroup) {
                ((ViewManager) view.getParent()).removeView(view);
            }
            view = null;
            isDestroyed = true;
        }
    }

    @Override
    public void onGlobalLayout() {
        if (isFirstLayout && isViewShown()) {
            onViewDidLoad();
            isFirstLayout = false;
        }
        if (!isShown && isViewShown()) {
            isShown = true;
            onViewAppeared();
            if (this instanceof ChildController) {
                Navigator.getInstance().getChildRegistry().onViewAppeared((ChildController) this);
            }
        } else if (isShown && !isViewShown()) {
            isShown = false;
            onViewDisappear();
            if (this instanceof ChildController) {
                Navigator.getInstance().getChildRegistry().onViewDisappear((ChildController) this);
            }
        }
    }

    public ViewAnimator getPushViewAnimator() {
        return pushViewAnimator;
    }

    public ViewAnimator getPopViewAnimator() {
        return popViewAnimator;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {

    }

    @Override
    public void onChildViewRemoved(View parent, View child) {

    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, T child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        perform(findController(child), ViewController::applyTopInset);
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, T child, View dependency) {
        return false;
    }

    public void applyTopInset() {

    }

    public int getTopInset() {
        return 0;
    }

    public void applyBottomInset() {

    }

    public int getBottomInset() {
        return perform(parentController, 0, p -> p.getBottomInset(this));
    }

    public void performOnParentStack(Func1<StackController> task) {
        if (parentController instanceof StackController) {
            task.run((StackController) parentController);
        } else if (this instanceof StackController) {
            task.run((StackController) this);
        } else performOnParentController(parent -> parent.performOnParentStack(task));
    }
}
