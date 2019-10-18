package com.mifeng.mf.MFNavigation.Views;

import android.app.Activity;

import com.mifeng.mf.MFNavigation.Impl.MFView;
import com.mifeng.mf.MFNavigation.Interface.IMFView;


public class MFComponentViewCreator implements MFViewCreator {

	public MFComponentViewCreator() {
	}

	@Override
	public IMFView create(final Activity activity, final String componentId) {
		return new MFView(activity, componentId);
	}
}
