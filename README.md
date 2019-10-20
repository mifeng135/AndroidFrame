

# [中文文档](https://github.com/mifeng135/AndroidFrame/blob/master/README-zh.md)

# navigator 

|function|Description|
|:---|:---|
|push|Keep the current page and jump to another page within the app (but not the TabViewController page)|
|pop|Close current view，back to previous page|
|popToRoot|Close all other non-TabViewController page|
|popTo|Close the current page, return to the previous page or multi-level page|
|switchTab|Jump to TabViewController page and close all other non-TabViewController page|
|replace|Close the current page and jump to another page within the app|


# page life cycle

|function|Description|
|:---|:---|
|onViewDidLoad|call once at page init complete| 
|onViewAppeared|will be called at page show|
|onViewDisappear|will be called at page hide|
|onViewDidUnload|call once at page destroy|


# pageAnimator
 1. pushViewAnimator 
 2. popViewAnimator
### Examples
```
	pushViewAnimator = ViewAnimator.animate(view).alpha(0, 1).startDelay(400).duration(450).toViewAnimator();
	popViewAnimator = ViewAnimator.animate(view).alpha(1, 0).duration(400).toViewAnimator();
```	


# support swipelayout
### MFSwipeBackLayout
```
public class SampleClass extends UIViewController<MFSwipeBackLayout> implements ChildViewController
```

#sample ViewController
```
public class SampleViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController{

    public SampleViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new SampleViewControllerLogic(this);
    }

    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();
        LinearLayout linearLayout = createLinearLayout();
        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setMFCenterText("例子界面");
        mfTitleBar.setListener(new MFTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ACTION_LEFT_BUTTON) {
                    Navigator.getInstance().pop(getId());
                }
            }
        });
        View baseView = inflater.inflate(R.layout.sample_layout, view, false);
        linearLayout.addView(mfTitleBar);
        linearLayout.addView(baseView);
        view.addView(linearLayout);
        return view;
    }

    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
    }
    @Override
    public void onViewAppeared() {
        super.onViewAppeared();
    }
    @Override
    public void onViewDisappear() {
        super.onViewDisappear();
    }
    @Override
    public void onViewDidUnload() {
        super.onViewDidUnload();
    }
```

if you want create TabViewController you can do like this
```
public class SampleViewController extends UIViewController<MFSwipeBackLayout> implements TabViewController
```

# http request

```
	1. at MFMsgUrl file
		public static final String CATALOG_HOME_TOP_STORE_DATA = "/test/test/store-data";
	2. at MFMsgDefine file 
		public static final String MSG_SEND_CATALOG_HOME_TOP_STORE_INFO                         = "1001";
		public static final String MSG_RECV_CATALOG_HOME_TOP_STORE_INFO                         = "2001";
	3. at MFMsgFactory file 
		mMsgSendFactory.put(MFMsgDefine.MSG_SEND_CATALOG_HOME_TOP_STORE_INFO, MFMsgUrl.CATALOG_HOME_TOP_STORE_DATA);
		mMsgRecvFactory.put(MFMsgUrl.CATALOG_HOME_TOP_STORE_DATA, MFMsgDefine.MSG_RECV_CATALOG_HOME_TOP_STORE_INFO);
	4. at MFBeanFactory file
		mBeanFactory.put(MFMsgDefine.MSG_RECV_CATALOG_HOME_TOP_STORE_INFO, TestBean.class);
	5. logic file will hava method 
		@Subscribe(threadMode = ThreadMode.MAIN)
		public void onRecvMsg(MFPostEvent messageEvent) {
			String eventCmd = messageEvent.getEventCmd();
			if (eventCmd == MFMsgDefine.MSG_RECV_CATALOG_HOME_TOP_STORE_INFO) {
				TestBean testBean = (TestBean) messageEvent.getData();
			}
		}
```
# support simple mvc mode

# dome
[download dome.apk](https://github.com/mifeng135/AndroidFrame/blob/master/debug.apk)

<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/1.png"/></div>
<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/4.png"/></div>
<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/5.png"/></div>






