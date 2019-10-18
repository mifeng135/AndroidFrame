package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewGroup;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.mifeng.mf.MFNavigation.Animation.NavigationAnimator;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Utils.CompatUtils;
import com.mifeng.mf.MFNavigation.Views.StackLayout;

import static com.mifeng.mf.MFNavigation.Utils.CoordinatorLayoutUtils.matchParentWithBehaviour;


public class StackController extends ParentController<StackLayout> {

    private IdStack<ViewController> stack = new IdStack<>();
    private final NavigationAnimator animator;


    public StackController(Activity activity,ChildControllersRegistry childRegistry, NavigationAnimator animator, String id,List<ViewController> children) {
        super(activity, id,childRegistry);
        this.animator = animator;
        for (ViewController child : children) {
            child.setParentController(this);
            stack.push(child.getId(), child);
        }
    }

    @Override
    protected ViewController getCurrentChild() {
        return stack.peek();
    }

    public void onChildDestroyed(ViewController child) {
        super.onChildDestroyed(child);
    }

    public void push(ViewController child) {
        if (stack.containsId(child.getId())) {
            return;
        }
        Navigator.getInstance().getBottomTabsController().getBottomTabs().hideBottomNavigation(true);
        final ViewController toRemove = stack.peek();
        child.setParentController(this);
        stack.push(child.getId(), child);
        addChildToStack(child.getView());

        if (toRemove != null) {
            if (toRemove instanceof ChildViewController) {
                toRemove.onViewDisappear();
            }
            animator.push(child.getView(), () -> {
            });
        }
    }

    public void push(ViewController child, Runnable onAnimationEnd) {
        if (stack.containsId(child.getId())) {
            return;
        }
        Navigator.getInstance().getBottomTabsController().getBottomTabs().hideBottomNavigation(true);
        final ViewController toRemove = stack.peek();
        child.setParentController(this);
        stack.push(child.getId(), child);
        addChildToStack(child.getView());

        if (toRemove != null) {
            if (toRemove instanceof ChildViewController) {
                toRemove.onViewDisappear();
            }
            animator.push(child.getView(), onAnimationEnd);
        }
    }

    public void pushWithElementAnimator(ViewController child) {
        if (stack.containsId(child.getId())) {
            return;
        }
        Navigator.getInstance().getBottomTabsController().getBottomTabs().hideBottomNavigation(true);
        final ViewController toRemove = stack.peek();
        child.setParentController(this);
        stack.push(child.getId(), child);
        addChildToStack(child.getView());

        if (toRemove != null) {
            if (toRemove instanceof  ChildViewController) {
                toRemove.onViewDisappear();
            }
            animator.push(child);
        }
    }

    private void addChildToStack(View view) {
        getView().addView(view, getView().getChildCount() , matchParentWithBehaviour(new StackBehaviour(this)));
    }

    public void popWithElementAnimator() {
        if (!canPop()) {
            return;
        }

        final ViewController disappearing = stack.pop();
        final ViewController appearing = stack.peek();

        disappearing.onViewWillDisappear();
        appearing.onViewWillAppear();

        ViewGroup appearingView = appearing.getView();
        if (appearingView.getLayoutParams() == null) {
            appearingView.setLayoutParams(matchParentWithBehaviour(new StackBehaviour(this)));
        }
        if (appearingView.getParent() == null) {
            getView().addView(appearingView, 0);
        }
        animator.pop(disappearing,() -> finishPopping(disappearing));
    }

    public void pop(boolean animation) {
        if (!canPop()) {
            return;
        }

        final ViewController disappearing = stack.pop();
        final ViewController appearing = stack.peek();

        disappearing.onViewWillDisappear();
        appearing.onViewWillAppear();

        ViewGroup appearingView = appearing.getView();
        if (appearingView.getLayoutParams() == null) {
            appearingView.setLayoutParams(matchParentWithBehaviour(new StackBehaviour(this)));
        }
        if (appearingView.getParent() == null) {
            getView().addView(appearingView, 0);
        }
        if (animation) {
            animator.pop(disappearing.getView(), () -> finishPopping(disappearing));
        }else {
            finishPopping(disappearing);
        }
    }

    private void finishPopping(ViewController disappearing) {
        if (stack.size() == 1) {
            Navigator.getInstance().getBottomTabsController().getBottomTabs().restoreBottomNavigation(true);
        }
        disappearing.destroy();
    }

    public void popTo(ViewController viewController) {
        if (!stack.containsId(viewController.getId()) || peek().equals(viewController)) {
            return;
        }

        animator.cancelPushAnimations();
        String currentControlId;
        for (int i = stack.size() - 2; i >= 0; i--) {
            currentControlId = stack.get(i).getId();
            if (currentControlId.equals(viewController.getId())) {
                break;
            }

            ViewController controller = stack.get(currentControlId);
            stack.remove(controller.getId());
            controller.destroy();
        }

        pop(true);
    }

    public void popToRoot() {
        if (!canPop()) {
            return;
        }
        animator.cancelPushAnimations();
        Iterator<String> iterator = stack.iterator();
        iterator.next();
        while (stack.size() > 2) {
            ViewController controller = stack.get(iterator.next());
            if (!stack.isTop(controller.getId())) {
                stack.remove(iterator, controller.getId());
                controller.destroy();
            }
        }

        pop(true);
    }

    public void replace(ViewController child) {
        if (!canPop()) {
            return;
        }
        animator.cancelPushAnimations();
        Iterator<String> iterator = stack.iterator();
        iterator.next();


        if (stack.size() > 1) {
            ViewController controller = stack.get(iterator.next());
            stack.remove(iterator, controller.getId());
            controller.destroy();
        }
        push(child);
    }

    public void switchTab(int index) {
        if (!canPop()) {
            return;
        }
        animator.cancelPushAnimations();
        Iterator<String> iterator = stack.iterator();
        iterator.next();
        while (stack.size() > 1) {
            ViewController controller = stack.get(iterator.next());
            if (!stack.isTop(controller.getId())) {
                stack.remove(iterator, controller.getId());
                controller.destroy();
            }else {
                stack.remove(iterator, controller.getId());
                controller.destroy();
            }
        }
        Navigator.getInstance().getBottomTabsController().getBottomTabs().restoreBottomNavigation(false);
        Navigator.getInstance().getBottomTabsController().selectTab(index);
    }

    ViewController peek() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    boolean canPop() {
        return stack.size() > 1;
    }

    @NonNull
    @Override
    protected StackLayout createView() {
        StackLayout stackLayout = new StackLayout(getActivity(), getId());
        addInitialChild(stackLayout);
        return stackLayout;
    }

    private void addInitialChild(StackLayout stackLayout) {
        if (isEmpty()) return;
        ViewGroup child = peek().getView();
        child.setId(CompatUtils.generateViewId());
        stackLayout.addView(child, 0, matchParentWithBehaviour(new StackBehaviour(this)));
    }


    @NonNull
    @Override
    public Collection<ViewController> getChildControllers() {
        return stack.values();
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ViewGroup child, View dependency) {
        return false;
    }

    @Override
    public int getTopInset(ViewController child) {
        return 0;
    }

}
