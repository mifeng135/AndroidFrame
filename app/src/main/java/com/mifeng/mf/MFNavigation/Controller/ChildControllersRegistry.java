package com.mifeng.mf.MFNavigation.Controller;

import android.view.View;

import java.util.ArrayDeque;

import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.perform;

public class ChildControllersRegistry {
    private ArrayDeque<ChildController> children = new ArrayDeque<>();

    public void onViewAppeared(ChildController child) {
        ChildController childController = children.peek();
        if ((childController instanceof TabViewController) && !(child instanceof StackController)) {
            childController.onViewDisappear();
        }
        assignViewState(false);
        children.push(child);
    }

    public void onViewDisappear(ChildController child) {
        if (isTopChild(child)) {
            children.pop();
        } else {
            children.remove(child);
        }
    }

    private boolean isTopChild(ChildController child) {
        return perform(children.peek(), false, c -> c.equals(child));
    }

    public void onChildDestroyed(ChildController child) {
        children.remove(child);

        ChildController controller = children.peek();
        if (controller instanceof TabViewController) {
            controller.onViewAppeared();
        }
        assignViewState(true);
    }

    public ArrayDeque<ChildController> getChildren() {
        return children;
    }

    public void assignViewState(boolean show) {
        Object [] objects = children.toArray();
        if (objects.length > 2) {
            Object oc = objects[1];
            if (oc instanceof ChildViewController) {
                if (show) {
                    ((ChildController)oc).getView().setVisibility(View.VISIBLE);
                }else {
                    ((ChildController)oc).getView().setVisibility(View.GONE);
                }
            }
        }
    }
}
