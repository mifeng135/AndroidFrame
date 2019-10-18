package com.mifeng.mf.MFNavigation.Navigator;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Collections;

import com.mifeng.mf.MFNavigation.Controller.BottomTabsController;
import com.mifeng.mf.MFNavigation.Controller.ChildControllersRegistry;
import com.mifeng.mf.MFNavigation.Controller.ParentController;
import com.mifeng.mf.MFNavigation.Controller.StackController;
import com.mifeng.mf.MFNavigation.Controller.ViewController;
import com.mifeng.mf.MFNavigation.Presenter.RootPresenter;
import com.mifeng.mf.MFNavigation.Utils.CommandListener;
import com.mifeng.mf.MFNavigation.Utils.CommandListenerAdapter;
import com.mifeng.mf.MFNavigation.Utils.CompatUtils;
import com.mifeng.mf.MFNavigation.Utils.Functions;

/**
 * Created by Administrator on 2019/8/20.
 */

public class Navigator extends ParentController {

    private ViewController root;
    private ViewController previousRoot;
    private final CoordinatorLayout rootLayout;

    private RootPresenter rootPresenter;

    private static volatile Navigator mInstance;

    public Navigator(final Activity activity, ChildControllersRegistry childRegistry, RootPresenter rootPresenter) {
        super(activity, "navigator" + CompatUtils.generateViewId(), childRegistry);
        rootLayout = new CoordinatorLayout(getActivity());
        this.rootPresenter = rootPresenter;
        mInstance = this;
    }


    public static Navigator getInstance() {
        return mInstance;
    }


    public CoordinatorLayout getRootLayout() {
        return rootLayout;
    }


    public void setContentLayout(ViewGroup contentLayout) {
        contentLayout.addView(rootLayout);
    }

    public void bindViews() {
        rootPresenter.setRootContainer(rootLayout);
    }

    @NonNull
    @Override
    protected ViewGroup createView() {
        return rootLayout;
    }

    @NonNull
    @Override
    public Collection<ViewController> getChildControllers() {
        return root == null ? Collections.emptyList() : Collections.singletonList(root);
    }

    @Override
    protected ViewController getCurrentChild() {
        return root;
    }


    @Override
    public void destroy() {
        destroyViews();
        super.destroy();
    }

    public void destroyViews() {
        destroyRoot();
    }

    private void destroyRoot() {
        if (root != null) root.destroy();
        root = null;
    }

    private void destroyPreviousRoot() {
        if (previousRoot != null) previousRoot.destroy();
        previousRoot = null;
    }


    public void setRoot(final ViewController viewController, CommandListener commandListener) {
        previousRoot = root;
        if (isRootNotCreated()) getView();
        root = viewController;
        rootPresenter.setRoot(root, new CommandListenerAdapter(commandListener) {
            @Override
            public void onSuccess(String childId) {
                super.onSuccess(childId);
                destroyPreviousRoot();
            }
        });
    }

    public void push(final String id, final ViewController viewController) {
        applyOnStack(id, (stack) -> stack.push(viewController));
    }

    public void pushWithElementAnimator(final String id, final ViewController viewController) {
        applyOnStack(id, (stack) -> stack.pushWithElementAnimator(viewController));
    }

    public void replace(final String id, final ViewController viewController) {
        ViewController target = findController(id);
        if (target != null) {
            applyOnStack(id, (stack) -> stack.replace(target,viewController));
        }
    }
    public void pop(String id) {
        applyOnStack(id, (stack) -> stack.pop( true));
    }

    public void popWithElementAnimator(String id) {
        applyOnStack(id, (stack) -> stack.popWithElementAnimator());
    }

    public void popSwipeBack(String id) {
        applyOnStack(id, (stack) -> stack.pop( false));
    }

    public void popToRoot(final String id) {
        applyOnStack(id, (stack) -> stack.popToRoot());
    }


    public void popTo(final String id) {
        ViewController target = findController(id);
        if (target != null) {
            target.performOnParentStack((stack) -> ((StackController) stack).popTo(target));
        }
    }

    public void switchTab(final String id, int index) {
        applyOnStack(id, (stack) -> stack.switchTab(index));
    }

    private void applyOnStack(String fromId, Functions.Func1<StackController> task) {
        ViewController from = findController(fromId);
        if (from != null) {
            from.performOnParentStack((stack) -> task.run((StackController) stack));
        }
    }

    private boolean isRootNotCreated() {
        return view == null;
    }


    public BottomTabsController getBottomTabsController() {
        return (BottomTabsController) root;
    }
}
