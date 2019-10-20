
# 导航支持

|方法|描述|
|:---|:---|
|push|保留当前页面，跳转到应用内的某个页面|
|pop|关闭当前页面，返回上一页面|
|popToRoot|并关闭其他所有非 tabBar 页面|
|popTo|关闭要跳转的界面之前的所有界面|
|switchTab|跳转到指定tabBar 页面，并关闭其他所有非 tabBar 页面|
|replace|关闭当前页面，跳转到应用内的某个页面。|

# 可以指定每个view节点 出现的动画方式
## 你可以编写类似的代码，可以指定将要出现或者消失的界面透明方式
```
pushViewAnimator = ViewAnimator.animate(mfTitleBar).alpha(0, 1).startDelay(400).duration(450).toViewAnimator();
popViewAnimator = ViewAnimator.animate(view).alpha(1, 0).duration(400).toViewAnimator();
```

# 界面的生命周期

|方法|描述|
|:---|:---|
|onViewDidLoad|界面初始化完成，只调用一次| 
|onViewAppeared|界面每次出现的时候调用|
|onViewDisappear|界面每次隐藏的时候调用|
|onViewDidUnload|界面销毁完成，只调用一次|


# 支持界面侧滑返回
## 指定MFSwipeBackLayout
```
public class SampleClass extends UIViewController<MFSwipeBackLayout> implements ChildViewController
```

# 支持简单的mvp模式

# [dome](https://github.com/mifeng135/AndroidFrame/blob/master/debug.apk)

<div style = "display:flex;flex-wrap: wrap">
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/1.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/3.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/4.png"/></div>
	<div><img width="450" height="750" src="https://github.com/mifeng135/AndroidFrame/blob/master/image/5.png"/></div>
</div>






