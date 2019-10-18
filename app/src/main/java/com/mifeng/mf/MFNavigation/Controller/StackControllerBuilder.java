package com.mifeng.mf.MFNavigation.Controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import com.mifeng.mf.MFNavigation.Animation.NavigationAnimator;

public class StackControllerBuilder {
    private Activity activity;
    private ChildControllersRegistry childRegistry;
    private String id;
    private NavigationAnimator animator;
    private List<ViewController> children = new ArrayList<>();

    public StackControllerBuilder(Activity activity) {
        this.activity = activity;
        animator = new NavigationAnimator(activity);
    }


    public StackControllerBuilder setChildren(List<ViewController> children) {
        this.children = children;
        return this;
    }

    public StackControllerBuilder setChildRegistry(ChildControllersRegistry childRegistry) {
        this.childRegistry = childRegistry;
        return this;
    }


    public StackControllerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public StackControllerBuilder setAnimator(NavigationAnimator animator) {
        this.animator = animator;
        return this;
    }

    public StackController build() {
        return new StackController(activity, childRegistry, animator, id,children);
    }
}