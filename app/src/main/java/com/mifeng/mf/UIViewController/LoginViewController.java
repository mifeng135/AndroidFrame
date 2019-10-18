package com.mifeng.mf.UIViewController;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import com.mifeng.mf.Common.MFCountDownTimer;
import com.mifeng.mf.Component.MFSwipeBackLayout.MFSwipeBackLayout;
import com.mifeng.mf.ControllerLogic.LoginViewControllerLogic;
import com.mifeng.mf.MFNavigation.Controller.ChildViewController;
import com.mifeng.mf.MFNavigation.Controller.UIViewController;
import com.mifeng.mf.MFNavigation.Navigator.Navigator;
import com.mifeng.mf.MFTitleBar.Widget.MFTitleBar;
import com.mifeng.mf.R;

import static android.view.View.generateViewId;
import static com.mifeng.mf.MFTitleBar.Widget.MFTitleBar.ACTION_LEFT_BUTTON;

/**
 * Created by Administrator on 2019/9/20.
 */

public class LoginViewController extends UIViewController<MFSwipeBackLayout> implements ChildViewController,View.OnClickListener{

    ImageView wechatLogin;
    TextView login,sms;
    EditText smsEdit,phoneEdit;
    MFCountDownTimer countDownTimer;

    public LoginViewController(Activity activity, String id, Map param) {
        super(activity, id);
        baseLogicHander = new LoginViewControllerLogic(this);
    }

    @NonNull
    @Override
    protected MFSwipeBackLayout createView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        view = createSwipeBackLayout();

        LinearLayout linearLayout = createLinearLayout();
        MFTitleBar mfTitleBar = createNormalTitleBar(inflater);
        mfTitleBar.setMFCenterText("登录");
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
        View baseView = inflater.inflate(R.layout.login_layout, view, false);

        wechatLogin = baseView.findViewById(R.id.login_wechat_image_view);
        wechatLogin.setOnClickListener(this);

        sms = baseView.findViewById(R.id.login_sms_text_view);
        sms.setOnClickListener(this);

        login = baseView.findViewById(R.id.login_button);
        login.setOnClickListener(this);


        phoneEdit = baseView.findViewById(R.id.login_phone_edit_text);

        smsEdit = baseView.findViewById(R.id.login_sms_edit_text);
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
        if (countDownTimer != null) {
            countDownTimer.cancle();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_wechat_image_view:
                String dd = phoneEdit.getText().toString();
                int d = 0;
                break;
            case R.id.login_button:
                int fff = 0;
                break;
            case R.id.login_sms_text_view:
//                countDownTimer = new MFCountDownTimer(
//                        sms,"#FFFFFF",
//                        getActivity().getResources().getDrawable(R.drawable.common_circle_layout_green),
//                        getActivity().getResources().getDrawable(R.drawable.common_circle_drawable_gray),60);
//                countDownTimer.start();

                FeedbackViewController feedbackViewController = new FeedbackViewController(getActivity(),generateViewId() + "",new HashMap());
                Navigator.getInstance().replace(getId(), feedbackViewController);

                break;
        }

    }
}
