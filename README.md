

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


# pageAnimator
 1. pushViewAnimator 
 2. popViewAnimator


# page life cycle

|function|Description|
|:---|:---|
|onViewDidLoad|call once at page init complete| 
|onViewAppeared|will be called at page show|
|onViewDisappear|will be called at page hide|
|onViewDidUnload|call once at page destroy|


# support swipe out
### MFSwipeBackLayout
```
public class SampleClass extends UIViewController<MFSwipeBackLayout> implements ChildViewController
```

# support simple mvc mode

# [dome](https://github.com/mifeng135/AndroidFrame/blob/master/debug.apk)

<div style = "display:flex;flex-wrap: wrap">
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/1.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/3.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/4.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/5.png"/></div>
</div>






