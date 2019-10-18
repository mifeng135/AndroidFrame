package com.mifeng.mf.MFNavigation.Views.BottomTab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.widget.LinearLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import com.mifeng.mf.R;

import static com.mifeng.mf.MFNavigation.Utils.ViewUtils.findChildByClass;

@SuppressLint("ViewConstructor")
public class BottomTabs extends AHBottomNavigation {

    public BottomTabs(Context context) {
        super(context);
        setId(R.id.bottomTabs);
        setBackgroundColor(Color.parseColor("#0000FF"));
        setBehaviorTranslationEnabled(false);
        setTitleState(TitleState.ALWAYS_SHOW);
    }

    @Override
    protected void createItems() {
        superCreateItems();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

    }

    public void superCreateItems() {
        super.createItems();
    }

    @Override
    public void setCurrentItem(@IntRange(from = 0) int position) {
        super.setCurrentItem(position);
    }

    @Override
    public void setTitleState(TitleState titleState) {
        if (getTitleState() != titleState) super.setTitleState(titleState);
    }

    public void setText(int index, String text) {
        AHBottomNavigationItem item = getItem(index);
        if (!item.getTitle(getContext()).equals(text)) {
            item.setTitle(text);
            refresh();
        }
    }

    public void setIcon(int index, Drawable icon) {
        AHBottomNavigationItem item = getItem(index);
        if (!item.getDrawable(getContext()).equals(icon)) {
            item.setDrawable(icon);
            refresh();
        }
    }

    public void setLayoutDirection(LayoutDirection direction) {
         LinearLayout tabsContainer = findChildByClass(this, LinearLayout.class);
        if (tabsContainer != null) tabsContainer.setLayoutDirection(direction.get());
    }
}
