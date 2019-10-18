package com.mifeng.mf.UIViewController;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mifeng.mf.Adapter.FeedbackGridImageAdapter;
import com.mifeng.mf.Common.MFActivityEventListener;
import com.mifeng.mf.Component.MFFullyGridLayoutManager;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.ControllerLogic.FeedbackViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.ChildViewController;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.MFViewAnimator.ViewAnimator;
import com.mifeng.mf.MainActivity;
import com.mifeng.mf.R;

import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_LEFT_BUTTON;
import static com.luck.picture.lib.config.PictureMimeType.ofImage;

/**
 * Created by Administrator on 2019/9/20.
 */

public class FeedbackViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController {


    RecyclerView selectorRv;
    private FeedbackGridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();


    public FeedbackViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new FeedbackViewControllerLogic(this);
    }

    @NonNull
    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();
        LinearLayout linearLayout = createLinearLayout();

        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setMFCenterText("建议反馈");
        mfTitleBar.setMFBackgroundColor(Color.parseColor("#FFFFFF"));
        mfTitleBar.setMFCenterTextColor(Color.parseColor("#000000"));
        mfTitleBar.setListener(new MFTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == ACTION_LEFT_BUTTON) {
                    Navigator.getInstance().pop(getId());
                }
            }
        });
        View baseView = inflater.inflate(R.layout.feed_back_layout, view, false);
        linearLayout.addView(mfTitleBar);
        linearLayout.addView(baseView);

        view.addView(linearLayout);
        initPictureselector(baseView);
        assignPushViewAnimator(baseView);
        assignPopViewAnimator(baseView);
        return view;
    }

    private void assignPushViewAnimator(View baseView) {
        pushViewAnimator = ViewAnimator.animate(view).alpha(0, 1).duration(300).toViewAnimator();
    }

    private void assignPopViewAnimator(View baseView) {
        popViewAnimator = ViewAnimator.animate(view).alpha(1, 0).duration(300).toViewAnimator();
    }

    private void initPictureselector(View view) {
        selectorRv = view.findViewById(R.id.feed_back_rv);
        MFFullyGridLayoutManager manager = new MFFullyGridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        selectorRv.setLayoutManager(manager);
        adapter = new FeedbackGridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(4);
        selectorRv.setAdapter(adapter);
    }

    private FeedbackGridImageAdapter.onAddPicClickListener onAddPicClickListener = new FeedbackGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(getActivity())
                    .openGallery(ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(4)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)
                    .isCamera(true)
                    .isZoomAnim(true)
                    .enableCrop(false)
                    .compress(true)
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)
                    .hideBottomControls(false)
                    .isGif(false)
                    .freeStyleCropEnabled(true)
                    .circleDimmedLayer(false)
                    .showCropFrame(false)
                    .showCropGrid(false)
                    .openClickSound(false)
                    .minimumCompressSize(100)
                    .selectionMedia(selectList)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

    };

    @Override
    public void onViewDidLoad() {
        super.onViewDidLoad();
        ((MainActivity) getActivity()).setMFActivityEventListener(mActivityEventListener);
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
        ((MainActivity) getActivity()).setMFActivityEventListener(null);
    }

    private final MFActivityEventListener mActivityEventListener = new MFActivityEventListener() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, final Intent data) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                selectList = PictureSelector.obtainMultipleResult(data);
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
