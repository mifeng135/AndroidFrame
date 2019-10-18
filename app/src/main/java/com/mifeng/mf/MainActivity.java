package com.mifeng.mf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;

import com.mifeng.mf.Common.MFActivityEventListener;
import com.mifeng.mf.Common.MFUtils;
import com.mifeng.mf.MFNavigation.Controller.BottomTabsController;
import com.mifeng.mf.MFNavigation.Controller.ChildController;
import com.mifeng.mf.MFNavigation.Controller.ChildControllersRegistry;
import com.mifeng.mf.MFNavigation.Controller.LayoutFactory;
import com.mifeng.mf.MFNavigation.Controller.StackController;
import com.mifeng.mf.MFNavigation.Controller.ViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFNavigation.Presenter.RootPresenter;
import com.mifeng.mf.MFNavigation.Utils.CommandListenerAdapter;
import com.mifeng.mf.TabViewController.CartViewController;
import com.mifeng.mf.TabViewController.CategoryViewController;
import com.mifeng.mf.TabViewController.HomeViewController;
import com.mifeng.mf.TabViewController.MineViewController;

public class MainActivity extends AppCompatActivity {

    protected Navigator navigator;

    private MFActivityEventListener mFActivityEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MFUtils(this);
        new MFGLogic(this, getWindow());
        navigator = new Navigator(this, new ChildControllersRegistry(), new RootPresenter());
        navigator.bindViews();
        new LayoutFactory(this, navigator.getChildRegistry());
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navigator.setContentLayout(findViewById(android.R.id.content));
        onInitNavigator();
    }

    public void onInitNavigator() {
        HomeViewController homeController = new HomeViewController(this, "HomeViewController", new HashMap());
        CategoryViewController categoryController = new CategoryViewController(this, "CategoryViewController", new HashMap());
        CartViewController cartController = new CartViewController(this, "CartViewController", new HashMap());
        MineViewController mineController = new MineViewController(this, "MineViewController", new HashMap());

        StackController homeStack = (StackController) LayoutFactory.getInstance().createStack(Arrays.asList(homeController));
        StackController categoryStack = (StackController) LayoutFactory.getInstance().createStack(Arrays.asList(categoryController));
        StackController cartStack = (StackController) LayoutFactory.getInstance().createStack(Arrays.asList(cartController));
        StackController miniStack = (StackController) LayoutFactory.getInstance().createStack(Arrays.asList(mineController));

        BottomTabsController bottomTabsController = (BottomTabsController) LayoutFactory.getInstance().createBottomTabs(Arrays.asList(homeStack, categoryStack, cartStack, miniStack));
        Navigator.getInstance().setRoot(bottomTabsController, new CommandListenerAdapter());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ArrayDeque<ChildController> childControllers = Navigator.getInstance().getChildRegistry().getChildren();
            if (childControllers.size() > 4) {
                ViewController viewController = childControllers.peek();
                String id = viewController.getId();
                if (viewController.getPopViewAnimator() != null) {
                    Navigator.getInstance().popWithElementAnimator(id);
                }else {
                    Navigator.getInstance().pop(id);
                }
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, me)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(me);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFActivityEventListener != null) {
            mFActivityEventListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setMFActivityEventListener(MFActivityEventListener activityEventListener) {
        mFActivityEventListener = activityEventListener;
    }

}
