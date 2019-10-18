package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

import com.mifeng.mf.MFNavigation.Views.Component;

import static com.mifeng.mf.MFNavigation.Utils.CollectionUtils.forEach;
import static com.mifeng.mf.MFNavigation.Utils.ObjectUtils.perform;

/**
 * Created by Administrator on 2019/8/19.
 */

public abstract class ParentController<T extends ViewGroup> extends ChildController  {

    protected ParentController(Activity activity, String id, ChildControllersRegistry childRegistry) {
        super(activity, id, childRegistry);
    }

    public boolean isCurrentChild(ViewController child) {
        return getCurrentChild() == child;
    }

    protected abstract ViewController getCurrentChild();

    @NonNull
    @Override
    public T getView() {
        return (T) super.getView();
    }

    @NonNull
    @Override
    protected abstract T createView();

    @NonNull
    public abstract Collection<? extends ViewController> getChildControllers();


    @Nullable
    @Override
    public ViewController findController(final String id) {
        ViewController fromSuper = super.findController(id);
        if (fromSuper != null) return fromSuper;

        for (ViewController child : getChildControllers()) {
            ViewController fromChild = child.findController(id);
            if (fromChild != null) return fromChild;
        }

        return null;
    }

    @Nullable
    @Override
    public ViewController findController(View child) {
        ViewController fromSuper = super.findController(child);
        if (fromSuper != null) return fromSuper;

        for (ViewController childController : getChildControllers()) {
            ViewController fromChild = childController.findController(child);
            if (fromChild != null) return fromChild;
        }

        return null;
    }


    @Override
    public boolean containsComponent(Component component) {
        if (super.containsComponent(component)) {
            return true;
        }
        for (ViewController child : getChildControllers()) {
            if (child.containsComponent(component)) return true;
        }
        return false;
    }


    @Override
    public void destroy() {
        super.destroy();
        forEach(getChildControllers(), ViewController::destroy);
    }

    public void onChildDestroyed(ViewController child) {

    }

    @Override
    public void applyTopInset() {
        forEach(getChildControllers(), ViewController::applyTopInset);
    }

    public int getTopInset(ViewController child) {
        return perform(getParentController(), 0, p -> p.getTopInset(child));
    }

    @Override
    public void applyBottomInset() {
        forEach(getChildControllers(), ViewController::applyBottomInset);
    }

    public int getBottomInset(ViewController child) {
        return perform(getParentController(), 0, p -> p.getBottomInset(child));
    }
}
