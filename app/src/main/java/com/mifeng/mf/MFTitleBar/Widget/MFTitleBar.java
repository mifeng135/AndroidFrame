package com.mifeng.mf.MFTitleBar.Widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mifeng.mf.MFTitleBar.Statusbar.StatusBarUtils;
import com.mifeng.mf.MFTitleBar.Utils.ScreenUtils;
import com.mifeng.mf.R;

import q.rorbin.badgeview.QBadgeView;

import static android.widget.ImageView.ScaleType.CENTER_INSIDE;


public class MFTitleBar extends RelativeLayout implements View.OnClickListener {

    private View viewStatusBarFill;                     // 状态栏填充视图
    private View viewBottomLine;                        // 分隔线视图
    private View viewBottomShadow;                      // 底部阴影
    private RelativeLayout rlMain;                      // 主视图

    private TextView tvLeft;                            // 左边TextView
    private ImageView btnLeft;                        // 左边ImageButton
    private View viewCustomLeft;
    private TextView tvRight;                           // 右边TextView
    private ImageView btnRight;                       // 右边ImageButton
    private ImageView btnRightSecond;                   //
    private View viewCustomRight;
    private LinearLayout llMainCenter;
    private TextView tvCenter;                          // 标题栏文字
    private TextView tvCenterSub;                       // 副标题栏文字
    private ProgressBar progressCenter;                 // 中间进度条,默认隐藏
    private RelativeLayout rlMainCenterSearch;          // 中间搜索框布局
    private EditText etSearchHint;
    private ImageView ivSearch;
    private ImageView ivVoice;
    private View centerCustomView;                      // 中间自定义视图

    private boolean fillStatusBar;                      // 是否撑起状态栏, true时,标题栏浸入状态栏
    private int titleBarColor;                          // 标题栏背景颜色
    private int titleBarHeight;                         // 标题栏高度
    private int statusBarColor;                         // 状态栏颜色
    private int statusBarMode;                          // 状态栏模式

    private boolean showBottomLine;                     // 是否显示底部分割线
    private int bottomLineColor;                        // 分割线颜色
    private float bottomShadowHeight;                   // 底部阴影高度

    private int leftType;                               // 左边视图类型
    private String leftText;                            // 左边TextView文字
    private int leftTextColor;                          // 左边TextView颜色
    private float leftTextSize;                         // 左边TextView文字大小
    private int leftDrawable;                           // 左边TextView drawableLeft资源
    private float leftDrawablePadding;                  // 左边TextView drawablePadding
    private int leftImageResource;                      // 左边图片资源
    private int leftCustomViewRes;                      // 左边自定义视图布局资源

    private int rightType;                              // 右边视图类型
    private String rightText;                           // 右边TextView文字
    private int rightTextColor;                         // 右边TextView颜色
    private float rightTextSize;                        // 右边TextView文字大小
    private int rightImageResource;                     // 右边图片资源
    private int rightSecondImageResource;
    private int rightCustomViewRes;                     // 右边自定义视图布局资源

    private int centerType;                             // 中间视图类型
    private String centerText;                          // 中间TextView文字
    private int centerTextColor;                        // 中间TextView字体颜色
    private float centerTextSize;                       // 中间TextView字体大小
    private boolean centerTextMarquee;                  // 中间TextView字体是否显示跑马灯效果
    private String centerSubText;                       // 中间subTextView文字
    private int centerSubTextColor;                     // 中间subTextView字体颜色
    private float centerSubTextSize;                    // 中间subTextView字体大小
    private boolean centerSearchEditable;                // 搜索框是否可输入
    private int centerSearchBgResource;                 // 搜索框背景图片
    private int centerSearchRightType;                  // 搜索框右边按钮类型  0: voice 1: delete
    private int centerCustomViewRes;                    // 中间自定义布局资源


    private int PADDING_5;
    private int PADDING_12;

    private MFTitleBar.OnTitleBarListener listener;
    private MFTitleBar.OnTitleBarDoubleClickListener doubleClickListener;

    private static final int TYPE_LEFT_NONE = 0;
    private static final int TYPE_LEFT_TEXTVIEW = 1;
    private static final int TYPE_LEFT_IMAGEBUTTON = 2;
    private static final int TYPE_LEFT_CUSTOM_VIEW = 3;

    private static final int TYPE_RIGHT_NONE = 0;
    private static final int TYPE_RIGHT_TEXTVIEW = 1;
    private static final int TYPE_RIGHT_IMAGEBUTTON = 2;
    private static final int TYPE_RIGHT_CUSTOM_VIEW = 3;
    private static final int TYPE_RIGHT_TWO_IMAGEBUTTON = 4;

    private static final int TYPE_CENTER_NONE = 0;
    private static final int TYPE_CENTER_TEXTVIEW = 1;
    private static final int TYPE_CENTER_SEARCHVIEW = 2;
    private static final int TYPE_CENTER_CUSTOM_VIEW = 3;

    private static final int TYPE_CENTER_SEARCH_RIGHT_VOICE = 0;
    private static final int TYPE_CENTER_SEARCH_RIGHT_DELETE = 1;


    QBadgeView rightButtonBadge;

    public MFTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(context, attrs);
        initGlobalViews(context);
        initMainViews(context);
        rightButtonBadge = new QBadgeView(context);
    }

    private void loadAttributes(Context context, AttributeSet attrs) {
        PADDING_5 = ScreenUtils.dp2PxInt(context, 5);
        PADDING_12 = ScreenUtils.dp2PxInt(context, 12);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MFTitleBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fillStatusBar = array.getBoolean(R.styleable.MFTitleBar_fillStatusBar, true);
        }
        titleBarColor = array.getColor(R.styleable.MFTitleBar_titleBarColor, Color.parseColor("#ffffff"));
        titleBarHeight = (int) array.getDimension(R.styleable.MFTitleBar_titleBarHeight, ScreenUtils.dp2PxInt(context, 44));
        statusBarColor = array.getColor(R.styleable.MFTitleBar_statusBarColor, Color.parseColor("#ffffff"));
        statusBarMode = array.getInt(R.styleable.MFTitleBar_statusBarMode, 0);

        showBottomLine = array.getBoolean(R.styleable.MFTitleBar_showBottomLine, true);
        bottomLineColor = array.getColor(R.styleable.MFTitleBar_bottomLineColor, Color.parseColor("#dddddd"));
        bottomShadowHeight = array.getDimension(R.styleable.MFTitleBar_bottomShadowHeight, ScreenUtils.dp2PxInt(context, 0));

        leftType = array.getInt(R.styleable.MFTitleBar_leftType, TYPE_LEFT_NONE);
        if (leftType == TYPE_LEFT_TEXTVIEW) {
            leftText = array.getString(R.styleable.MFTitleBar_leftText);
            leftTextColor = array.getColor(R.styleable.MFTitleBar_leftTextColor, getResources().getColor(R.color.comm_titlebar_text_selector));
            leftTextSize = array.getDimension(R.styleable.MFTitleBar_leftTextSize, ScreenUtils.dp2PxInt(context, 16));
            leftDrawable = array.getResourceId(R.styleable.MFTitleBar_leftDrawable, 0);
            leftDrawablePadding = array.getDimension(R.styleable.MFTitleBar_leftDrawablePadding, 5);
        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            leftImageResource = array.getResourceId(R.styleable.MFTitleBar_leftImageResource, R.drawable.comm_titlebar_back_normal);
        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            leftCustomViewRes = array.getResourceId(R.styleable.MFTitleBar_leftCustomView, 0);
        }

        rightType = array.getInt(R.styleable.MFTitleBar_rightType, TYPE_RIGHT_NONE);
        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            rightText = array.getString(R.styleable.MFTitleBar_rightText);
            rightTextColor = array.getColor(R.styleable.MFTitleBar_rightTextColor, getResources().getColor(R.color.comm_titlebar_text_selector));
            rightTextSize = array.getDimension(R.styleable.MFTitleBar_rightTextSize, ScreenUtils.dp2PxInt(context, 16));
        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            rightImageResource = array.getResourceId(R.styleable.MFTitleBar_rightImageResource, 0);
        } else if (rightType == TYPE_RIGHT_TWO_IMAGEBUTTON) {
            rightImageResource = array.getResourceId(R.styleable.MFTitleBar_rightImageResource, 0);
            rightSecondImageResource = array.getResourceId(R.styleable.MFTitleBar_rightSecondImageResource, 0);
        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            rightCustomViewRes = array.getResourceId(R.styleable.MFTitleBar_rightCustomView, 0);
        }

        centerType = array.getInt(R.styleable.MFTitleBar_centerType, TYPE_CENTER_NONE);
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            centerText = array.getString(R.styleable.MFTitleBar_centerText);
            centerTextColor = array.getColor(R.styleable.MFTitleBar_centerTextColor, Color.parseColor("#333333"));
            centerTextSize = array.getDimension(R.styleable.MFTitleBar_centerTextSize, ScreenUtils.dp2PxInt(context, 18));
            centerTextMarquee = array.getBoolean(R.styleable.MFTitleBar_centerTextMarquee, true);
            centerSubText = array.getString(R.styleable.MFTitleBar_centerSubText);
            centerSubTextColor = array.getColor(R.styleable.MFTitleBar_centerSubTextColor, Color.parseColor("#666666"));
            centerSubTextSize = array.getDimension(R.styleable.MFTitleBar_centerSubTextSize, ScreenUtils.dp2PxInt(context, 11));
        } else if (centerType == TYPE_CENTER_SEARCHVIEW) {
            centerSearchEditable = array.getBoolean(R.styleable.MFTitleBar_centerSearchEditable, true);
            centerSearchBgResource = array.getResourceId(R.styleable.MFTitleBar_centerSearchBg, R.drawable.comm_titlebar_search_gray_shape);
            centerSearchRightType = array.getInt(R.styleable.MFTitleBar_centerSearchRightType, TYPE_CENTER_SEARCH_RIGHT_VOICE);
        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            centerCustomViewRes = array.getResourceId(R.styleable.MFTitleBar_centerCustomView, 0);
        }

        array.recycle();
    }

    private final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    private void initGlobalViews(Context context) {
        ViewGroup.LayoutParams globalParams = new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        setLayoutParams(globalParams);

        boolean transparentStatusBar = StatusBarUtils.supportTransparentStatusBar();

        if (fillStatusBar && transparentStatusBar) {
            int statusBarHeight = StatusBarUtils.getStatusBarHeight(context);
            viewStatusBarFill = new View(context);
            viewStatusBarFill.setId(StatusBarUtils.generateViewId());
            viewStatusBarFill.setBackgroundColor(statusBarColor);
            LayoutParams statusBarParams = new LayoutParams(MATCH_PARENT, statusBarHeight);
            statusBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            addView(viewStatusBarFill, statusBarParams);
        }
        rlMain = new RelativeLayout(context);
        rlMain.setId(StatusBarUtils.generateViewId());
        rlMain.setBackgroundColor(titleBarColor);
        LayoutParams mainParams = new LayoutParams(MATCH_PARENT, titleBarHeight);
        if (fillStatusBar && transparentStatusBar) {
            mainParams.addRule(RelativeLayout.BELOW, viewStatusBarFill.getId());
        } else {
            mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }
        if (showBottomLine) {
            mainParams.height = titleBarHeight - Math.max(1, ScreenUtils.dp2PxInt(context, 0.4f));
        } else {
            mainParams.height = titleBarHeight;
        }
        addView(rlMain, mainParams);

        if (showBottomLine) {
            viewBottomLine = new View(context);
            viewBottomLine.setBackgroundColor(bottomLineColor);
            LayoutParams bottomLineParams = new LayoutParams(MATCH_PARENT, Math.max(1, ScreenUtils.dp2PxInt(context, 0.4f)));
            bottomLineParams.addRule(RelativeLayout.BELOW, rlMain.getId());
            addView(viewBottomLine, bottomLineParams);
        } else if (bottomShadowHeight != 0) {
            viewBottomShadow = new View(context);
            viewBottomShadow.setBackgroundResource(R.drawable.comm_titlebar_bottom_shadow);
            LayoutParams bottomShadowParams = new LayoutParams(MATCH_PARENT, ScreenUtils.dp2PxInt(context, bottomShadowHeight));
            bottomShadowParams.addRule(RelativeLayout.BELOW, rlMain.getId());
            addView(viewBottomShadow, bottomShadowParams);
        }
    }

    private void initMainViews(Context context) {
        if (leftType != TYPE_LEFT_NONE) {
            initMainLeftViews(context);
        }
        if (rightType != TYPE_RIGHT_NONE) {
            initMainRightViews(context);
        }
        if (centerType != TYPE_CENTER_NONE) {
            initMainCenterViews(context);
        }
    }

    private void initMainLeftViews(Context context) {
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);

        if (leftType == TYPE_LEFT_TEXTVIEW) {
            tvLeft = new TextView(context);
            tvLeft.setId(StatusBarUtils.generateViewId());
            tvLeft.setText(leftText);
            tvLeft.setTextColor(leftTextColor);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            tvLeft.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            tvLeft.setSingleLine(true);
            tvLeft.setOnClickListener(this);
            if (leftDrawable != 0) {
                tvLeft.setCompoundDrawablePadding((int) leftDrawablePadding);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    tvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, 0, 0, 0);
                } else {
                    tvLeft.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0);
                }
            }
            tvLeft.setPadding(PADDING_12, 0, PADDING_12, 0);
            rlMain.addView(tvLeft, leftInnerParams);
        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            btnLeft = new ImageView(context);
            btnLeft.setId(StatusBarUtils.generateViewId());
            btnLeft.setBackgroundColor(Color.TRANSPARENT);
            btnLeft.setImageResource(leftImageResource);
            btnLeft.setScaleType(CENTER_INSIDE);
            btnLeft.setPadding(10, 10, 10, 10);
            btnLeft.setOnClickListener(this);
            LayoutParams layoutParams = new LayoutParams(ScreenUtils.dp2PxInt(getContext(), 30), ScreenUtils.dp2PxInt(getContext(), 30));
            layoutParams.leftMargin = ScreenUtils.dp2PxInt(getContext(), 10);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            rlMain.addView(btnLeft, layoutParams);
        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            viewCustomLeft = LayoutInflater.from(context).inflate(leftCustomViewRes, rlMain, false);
            if (viewCustomLeft.getId() == View.NO_ID) {
                viewCustomLeft.setId(StatusBarUtils.generateViewId());
            }
            rlMain.addView(viewCustomLeft, leftInnerParams);
        }
    }

    /**
     * 初始化主视图右边部分
     * -- add: adaptive RTL
     *
     * @param context 上下文
     */
    private void initMainRightViews(Context context) {
        LayoutParams rightInnerParams = new LayoutParams(ScreenUtils.dp2PxInt(getContext(), 30), ScreenUtils.dp2PxInt(getContext(), 30));
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rightInnerParams.rightMargin = ScreenUtils.dp2PxInt(getContext(), 10);

        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            tvRight = new TextView(context);
            tvRight.setId(StatusBarUtils.generateViewId());
            tvRight.setText(rightText);
            tvRight.setTextColor(rightTextColor);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
            tvRight.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            tvRight.setSingleLine(true);
            tvRight.setPadding(PADDING_12, 0, PADDING_12, 0);
            tvRight.setOnClickListener(this);
            rlMain.addView(tvRight, rightInnerParams);
        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            btnRight = new ImageView(context);
            btnRight.setId(StatusBarUtils.generateViewId());
            btnRight.setImageResource(rightImageResource);
            btnRight.setBackgroundColor(Color.TRANSPARENT);
            btnRight.setScaleType(CENTER_INSIDE);
            btnRight.setPadding(0, 0, 0, 0);
            btnRight.setOnClickListener(this);
            rlMain.addView(btnRight, rightInnerParams);
        } else if (rightType == TYPE_RIGHT_TWO_IMAGEBUTTON) {
            btnRight = new ImageView(context);
            btnRight.setId(StatusBarUtils.generateViewId());
            btnRight.setImageResource(rightImageResource);
            btnRight.setBackgroundColor(Color.TRANSPARENT);
            btnRight.setScaleType(CENTER_INSIDE);
            btnRight.setPadding(0, 0, 0, 0);
            btnRight.setOnClickListener(this);
            rlMain.addView(btnRight, rightInnerParams);


            LayoutParams rightSecondLayoutParams = new LayoutParams(ScreenUtils.dp2PxInt(getContext(), 30), ScreenUtils.dp2PxInt(getContext(), 30));
            rightSecondLayoutParams.addRule(RelativeLayout.LEFT_OF, btnRight.getId());
            rightSecondLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            rightSecondLayoutParams.rightMargin = ScreenUtils.dp2PxInt(getContext(), 10);

            btnRightSecond = new ImageView(context);
            btnRightSecond.setId(StatusBarUtils.generateViewId());
            btnRightSecond.setImageResource(rightSecondImageResource);
            btnRightSecond.setBackgroundColor(Color.TRANSPARENT);
            btnRightSecond.setScaleType(CENTER_INSIDE);
            btnRightSecond.setPadding(0, 0, 0, 0);

            btnRightSecond.setOnClickListener(this);
            rlMain.addView(btnRightSecond, rightSecondLayoutParams);
        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            viewCustomRight = LayoutInflater.from(context).inflate(rightCustomViewRes, rlMain, false);
            if (viewCustomRight.getId() == View.NO_ID) {
                viewCustomRight.setId(StatusBarUtils.generateViewId());
            }
            rlMain.addView(viewCustomRight, rightInnerParams);
        }
    }

    private void initMainCenterViews(Context context) {
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            llMainCenter = new LinearLayout(context);
            llMainCenter.setId(StatusBarUtils.generateViewId());
            llMainCenter.setGravity(Gravity.CENTER);
            llMainCenter.setOrientation(LinearLayout.VERTICAL);
            llMainCenter.setOnClickListener(this);

            LayoutParams centerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerParams.setMarginStart(PADDING_12);
            centerParams.setMarginEnd(PADDING_12);
            centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(llMainCenter, centerParams);
            tvCenter = new TextView(context);
            tvCenter.setText(centerText);
            tvCenter.setTextColor(centerTextColor);
            tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextSize);
            tvCenter.setGravity(Gravity.CENTER);
            tvCenter.setSingleLine(true);
            tvCenter.setMaxWidth((int) (ScreenUtils.getScreenPixelSize(context)[0] * 3 / 5.0));
            if (centerTextMarquee) {
                tvCenter.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tvCenter.setMarqueeRepeatLimit(-1);
                tvCenter.requestFocus();
                tvCenter.setSelected(true);
            }
            LinearLayout.LayoutParams centerTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenter, centerTextParams);
            progressCenter = new ProgressBar(context);
            progressCenter.setIndeterminateDrawable(getResources().getDrawable(R.drawable.comm_titlebar_progress_draw));
            progressCenter.setVisibility(View.GONE);
            int progressWidth = ScreenUtils.dp2PxInt(context, 18);
            LayoutParams progressParams = new LayoutParams(progressWidth, progressWidth);
            progressParams.addRule(RelativeLayout.CENTER_VERTICAL);
            progressParams.addRule(RelativeLayout.START_OF, llMainCenter.getId());
            rlMain.addView(progressCenter, progressParams);

            tvCenterSub = new TextView(context);
            tvCenterSub.setText(centerSubText);
            tvCenterSub.setTextColor(centerSubTextColor);
            tvCenterSub.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerSubTextSize);
            tvCenterSub.setGravity(Gravity.CENTER);
            tvCenterSub.setSingleLine(true);
            if (TextUtils.isEmpty(centerSubText)) {
                tvCenterSub.setVisibility(View.GONE);
            }

            LinearLayout.LayoutParams centerSubTextParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            llMainCenter.addView(tvCenterSub, centerSubTextParams);
        } else if (centerType == TYPE_CENTER_SEARCHVIEW) {
            rlMainCenterSearch = new RelativeLayout(context);
            rlMainCenterSearch.setBackgroundResource(centerSearchBgResource);
            LayoutParams centerParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
            centerParams.topMargin = ScreenUtils.dp2PxInt(context, 7);
            centerParams.bottomMargin = ScreenUtils.dp2PxInt(context, 7);
            if (leftType == TYPE_LEFT_TEXTVIEW) {
                centerParams.addRule(RelativeLayout.END_OF, tvLeft.getId());
                centerParams.setMarginStart(PADDING_5);
            } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
                centerParams.addRule(RelativeLayout.END_OF, btnLeft.getId());
                centerParams.setMarginStart(PADDING_5);
            } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
                centerParams.addRule(RelativeLayout.END_OF, viewCustomLeft.getId());
                centerParams.setMarginStart(PADDING_5);
            } else {
                centerParams.setMarginStart(PADDING_12);
            }
            if (rightType == TYPE_RIGHT_TEXTVIEW) {
                centerParams.addRule(RelativeLayout.START_OF, tvRight.getId());
                centerParams.setMarginEnd(PADDING_5);
            } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
                centerParams.addRule(RelativeLayout.START_OF, btnRight.getId());
                centerParams.setMarginEnd(PADDING_5);
            } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
                centerParams.addRule(RelativeLayout.START_OF, viewCustomRight.getId());
                centerParams.setMarginEnd(PADDING_5);
            } else {
                centerParams.setMarginEnd(PADDING_12);
            }
            rlMain.addView(rlMainCenterSearch, centerParams);

            // 初始化搜索框搜索ImageView
            ivSearch = new ImageView(context);
            ivSearch.setId(StatusBarUtils.generateViewId());
            ivSearch.setOnClickListener(this);
            int searchIconWidth = ScreenUtils.dp2PxInt(context, 15);
            LayoutParams searchParams = new LayoutParams(searchIconWidth, searchIconWidth);
            searchParams.addRule(RelativeLayout.CENTER_VERTICAL);
            searchParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            searchParams.setMarginStart(PADDING_12);
            rlMainCenterSearch.addView(ivSearch, searchParams);
            ivSearch.setImageResource(R.drawable.comm_titlebar_search_normal);

            ivVoice = new ImageView(context);
            ivVoice.setId(StatusBarUtils.generateViewId());
            ivVoice.setOnClickListener(this);
            LayoutParams voiceParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            voiceParams.addRule(RelativeLayout.CENTER_VERTICAL);
            voiceParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            voiceParams.setMarginEnd(PADDING_12);
            rlMainCenterSearch.addView(ivVoice, voiceParams);
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                ivVoice.setImageResource(R.drawable.comm_titlebar_voice);
            } else {
                ivVoice.setImageResource(R.drawable.comm_titlebar_delete_normal);
                ivVoice.setVisibility(View.GONE);
            }

            etSearchHint = new EditText(context);
            etSearchHint.setBackgroundColor(Color.TRANSPARENT);
            etSearchHint.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            etSearchHint.setHint("111");
            etSearchHint.setTextColor(Color.parseColor("#666666"));
            etSearchHint.setHintTextColor(Color.parseColor("#999999"));
            etSearchHint.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.dp2PxInt(context, 14));
            etSearchHint.setPadding(PADDING_5, 0, PADDING_5, 0);
            if (!centerSearchEditable) {
                etSearchHint.setCursorVisible(false);
                etSearchHint.clearFocus();
                etSearchHint.setFocusable(false);
                etSearchHint.setOnClickListener(this);
            }
            etSearchHint.setCursorVisible(false);
            etSearchHint.setSingleLine(true);
            etSearchHint.setEllipsize(TextUtils.TruncateAt.END);
            etSearchHint.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            etSearchHint.addTextChangedListener(centerSearchWatcher);
            etSearchHint.setOnFocusChangeListener(focusChangeListener);
            etSearchHint.setOnEditorActionListener(editorActionListener);
            if (centerSearchEditable) {
                etSearchHint.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etSearchHint.setCursorVisible(true);
                    }
                });
            }
            LayoutParams searchHintParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
            searchHintParams.addRule(RelativeLayout.END_OF, ivSearch.getId());
            searchHintParams.addRule(RelativeLayout.START_OF, ivVoice.getId());
            searchHintParams.addRule(RelativeLayout.CENTER_VERTICAL);
            searchHintParams.setMarginStart(PADDING_5);
            searchHintParams.setMarginEnd(PADDING_5);
            rlMainCenterSearch.addView(etSearchHint, searchHintParams);
        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            centerCustomView = LayoutInflater.from(context).inflate(centerCustomViewRes, rlMain, false);
            if (centerCustomView.getId() == View.NO_ID) {
                centerCustomView.setId(StatusBarUtils.generateViewId());
            }
            LayoutParams centerCustomParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
            centerCustomParams.setMarginStart(PADDING_12);
            centerCustomParams.setMarginEnd(PADDING_12);
            centerCustomParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            rlMain.addView(centerCustomView, centerCustomParams);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setUpImmersionTitleBar();
    }

    private void setUpImmersionTitleBar() {
        Window window = getWindow();
        if (window == null) return;
        StatusBarUtils.transparentStatusBar(window);
        if (statusBarMode == 0) {
            StatusBarUtils.setDarkMode(window);
        } else {
            StatusBarUtils.setLightMode(window);
        }
    }

    private Window getWindow() {
        Context context = getContext();
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        }
        if (activity != null) {
            return activity.getWindow();
        }
        return null;
    }

    private TextWatcher centerSearchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                if (TextUtils.isEmpty(s)) {
                    ivVoice.setImageResource(R.drawable.comm_titlebar_voice);
                } else {
                    ivVoice.setImageResource(R.drawable.comm_titlebar_delete_normal);
                }
            } else {
                if (TextUtils.isEmpty(s)) {
                    ivVoice.setVisibility(View.GONE);
                } else {
                    ivVoice.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_DELETE) {
                String input = etSearchHint.getText().toString();
                if (hasFocus && !TextUtils.isEmpty(input)) {
                    ivVoice.setVisibility(View.VISIBLE);
                } else {
                    ivVoice.setVisibility(View.GONE);
                }
            }
        }
    };

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (listener != null && actionId == EditorInfo.IME_ACTION_SEARCH) {
                listener.onClicked(v, ACTION_SEARCH_SUBMIT, etSearchHint.getText().toString());
            }
            return false;
        }
    };

    private long lastClickMillis = 0;     // 双击事件中，上次被点击时间

    @Override
    public void onClick(View v) {
        if (listener == null) return;

        if (v.equals(llMainCenter) && doubleClickListener != null) {
            long currentClickMillis = System.currentTimeMillis();
            if (currentClickMillis - lastClickMillis < 500) {
                doubleClickListener.onClicked(v);
            }
            lastClickMillis = currentClickMillis;
        } else if (v.equals(tvLeft)) {
            listener.onClicked(v, ACTION_LEFT_TEXT, null);
        } else if (v.equals(btnLeft)) {
            listener.onClicked(v, ACTION_LEFT_BUTTON, null);
        } else if (v.equals(tvRight)) {
            listener.onClicked(v, ACTION_RIGHT_TEXT, null);
        } else if (v.equals(btnRight)) {
            listener.onClicked(v, ACTION_RIGHT_BUTTON, null);
        } else if (v.equals(btnRightSecond)) {
            listener.onClicked(v, ACTION_RIGHT_SECOND_BUTTON, null);
        } else if (v.equals(etSearchHint) || v.equals(ivSearch)) {
            listener.onClicked(v, ACTION_SEARCH, null);
        } else if (v.equals(ivVoice)) {
            etSearchHint.setText("");
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                listener.onClicked(v, ACTION_SEARCH_VOICE, null);
            } else {
                listener.onClicked(v, ACTION_SEARCH_DELETE, null);
            }
        } else if (v.equals(tvCenter)) {
            listener.onClicked(v, ACTION_CENTER_TEXT, null);
        }
    }


    @Override
    public void setBackgroundResource(int resource) {
        setBackgroundColor(Color.TRANSPARENT);
        super.setBackgroundResource(resource);
    }

    public void setStatusBarColor(int color) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setBackgroundColor(color);
        }
    }

    public void showStatusBar(boolean show) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void toggleStatusBarMode() {
        Window window = getWindow();
        if (window == null) return;
        StatusBarUtils.transparentStatusBar(window);
        if (statusBarMode == 0) {
            statusBarMode = 1;
            StatusBarUtils.setLightMode(window);
        } else {
            statusBarMode = 0;
            StatusBarUtils.setDarkMode(window);
        }
    }

    public View getButtomLine() {
        return viewBottomLine;
    }

    public TextView getLeftTextView() {
        return tvLeft;
    }

    public TextView getRightTextView() {
        return tvRight;
    }

    public ImageView getRightImageButton() {
        return btnRight;
    }

    public LinearLayout getCenterLayout() {
        return llMainCenter;
    }

    public TextView getCenterTextView() {
        return tvCenter;
    }

    public TextView getCenterSubTextView() {
        return tvCenterSub;
    }

    public RelativeLayout getCenterSearchView() {
        return rlMainCenterSearch;
    }

    public EditText getCenterSearchEditText() {
        return etSearchHint;
    }

    public ImageView getCenterSearchRightImageView() {
        return ivVoice;
    }

    public ImageView getCenterSearchLeftImageView() {
        return ivSearch;
    }

    public View getLeftCustomView() {
        return viewCustomLeft;
    }

    public View getRightCustomView() {
        return viewCustomRight;
    }

    public View getCenterCustomView() {
        return centerCustomView;
    }

    public void setLeftView(View leftView) {
        if (leftView.getId() == View.NO_ID) {
            leftView.setId(StatusBarUtils.generateViewId());
        }
        LayoutParams leftInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(leftView, leftInnerParams);
    }

    public void setCenterView(View centerView) {
        if (centerView.getId() == View.NO_ID) {
            centerView.setId(StatusBarUtils.generateViewId());
        }
        LayoutParams centerInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(centerView, centerInnerParams);
    }

    public void setRightView(View rightView) {
        if (rightView.getId() == View.NO_ID) {
            rightView.setId(StatusBarUtils.generateViewId());
        }
        LayoutParams rightInnerParams = new LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        rlMain.addView(rightView, rightInnerParams);
    }

    public void showCenterProgress() {
        progressCenter.setVisibility(View.VISIBLE);
    }

    public void dismissCenterProgress() {
        progressCenter.setVisibility(View.GONE);
    }

    public void showSoftInputKeyboard(boolean show) {
        if (centerSearchEditable && show) {
            etSearchHint.setFocusable(true);
            etSearchHint.setFocusableInTouchMode(true);
            etSearchHint.requestFocus();
            ScreenUtils.showSoftInputKeyBoard(getContext(), etSearchHint);
        } else {
            ScreenUtils.hideSoftInputKeyBoard(getContext(), etSearchHint);
        }
    }

    public void setSearchRightImageResource(int res) {
        if (ivVoice != null) {
            ivVoice.setImageResource(res);
        }
    }

    public String getSearchKey() {
        if (etSearchHint != null) {
            return etSearchHint.getText().toString();
        }
        return "";
    }

    public void setListener(MFTitleBar.OnTitleBarListener listener) {
        this.listener = listener;
    }

    public void setDoubleClickListener(MFTitleBar.OnTitleBarDoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    public static final int ACTION_LEFT_TEXT = 1;        // 左边TextView被点击
    public static final int ACTION_LEFT_BUTTON = 2;      // 左边ImageBtn被点击
    public static final int ACTION_RIGHT_TEXT = 3;       // 右边TextView被点击
    public static final int ACTION_RIGHT_BUTTON = 4;     // 右边ImageBtn被点击
    public static final int ACTION_SEARCH = 5;           // 搜索框被点击,搜索框不可输入的状态下会被触发
    public static final int ACTION_SEARCH_SUBMIT = 6;    // 搜索框输入状态下,键盘提交触发
    public static final int ACTION_SEARCH_VOICE = 7;     // 语音按钮被点击
    public static final int ACTION_SEARCH_DELETE = 8;    // 搜索删除按钮被点击
    public static final int ACTION_CENTER_TEXT = 9;     // 中间文字点击
    public static final int ACTION_RIGHT_SECOND_BUTTON = 10; //右侧第二个按钮点击


    public interface OnTitleBarListener {
        void onClicked(View v, int action, String extra);
    }

    public interface OnTitleBarDoubleClickListener {
        void onClicked(View v);
    }


    /**
     * titlebar props
     * /
     ************************************************************************************************************/
    public MFTitleBar setMFSearchText(String searchText) {
        if (etSearchHint != null) {
            etSearchHint.setHint(searchText);
        }
        return this;
    }

    public MFTitleBar setMFBackgroundColor(int color) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setBackgroundColor(color);
        }
        rlMain.setBackgroundColor(color);
        return this;
    }

    public MFTitleBar setMFMainAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        rlMain.setAlpha(alpha);
        return this;
    }

    public MFTitleBar setMFStatusBarColor(int color) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill.setBackgroundColor(color);
        }
        return this;
    }

    public MFTitleBar setMFLeftText(String leftText) {
        if (tvLeft != null) {
            tvLeft.setText(leftText);
        }
        return this;
    }

    public MFTitleBar setMFLeftTextColor(int color) {
        if (tvLeft != null) {
            tvLeft.setTextColor(color);
        }
        return this;
    }

    public MFTitleBar setMFLeftTextSize(int size) {
        int textSize = ScreenUtils.dp2PxInt(getContext(), size);
        if (tvLeft != null) {
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        return this;
    }

    public MFTitleBar hideLeftIcon() {
        if (btnLeft != null) {
            btnLeft.setVisibility(INVISIBLE);
        }
        return this;
    }

    public MFTitleBar setMFLeftIcon(Drawable rightIcon, int width, int height) {
        LayoutParams layoutParams = new LayoutParams(ScreenUtils.dp2PxInt(getContext(), width), ScreenUtils.dp2PxInt(getContext(), height));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.leftMargin = ScreenUtils.dp2PxInt(getContext(), 10);
        if (btnLeft != null) {
            btnLeft.setLayoutParams(layoutParams);
            btnLeft.setImageDrawable(rightIcon);
        }
        return this;
    }

    public MFTitleBar setMFLeftIcon(Drawable rightIcon) {
        if (btnLeft != null) {
            btnLeft.setImageDrawable(rightIcon);
        }
        return this;
    }

    public MFTitleBar setMFCenterText(String centerText) {
        if (tvCenter != null) {
            tvCenter.setText(centerText);
        }
        return this;
    }

    public MFTitleBar setMFCenterTextColor(int color) {
        if (tvCenter != null) {
            tvCenter.setTextColor(color);
        }
        return this;
    }

    public MFTitleBar setMFCenterTextSize(int size) {
        int textSize = ScreenUtils.dp2PxInt(getContext(), size);
        if (tvCenter != null) {
            tvCenter.setTextSize(textSize);
        }
        return this;
    }

    public MFTitleBar setMFRightTextSize(int size) {
        int textSize = ScreenUtils.dp2PxInt(getContext(), size);
        if (tvRight != null) {
            tvRight.setTextSize(textSize);
        }
        return this;
    }

    public MFTitleBar setMFRightText(String rightText) {
        if (tvRight != null) {
            tvRight.setText(rightText);
        }
        return this;
    }

    public MFTitleBar setMFRightTextColor(int color) {
        if (tvRight != null) {
            tvRight.setTextColor(color);
        }
        return this;
    }


    public MFTitleBar setRightIcon(Drawable rightIcon, int width, int height) {
        LayoutParams layoutParams = new LayoutParams(ScreenUtils.dp2PxInt(getContext(), width), ScreenUtils.dp2PxInt(getContext(), height));
        layoutParams.rightMargin = ScreenUtils.dp2PxInt(getContext(), 10);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (btnRight != null) {
            btnRight.setLayoutParams(layoutParams);
            btnRight.setImageDrawable(rightIcon);
        }
        return this;
    }

    public MFTitleBar setRightBadge(int rightBadge) {
        if (btnRight != null) {
            if (rightButtonBadge.getTargetView() != null) {
                rightButtonBadge.setBadgeNumber(rightBadge);
            } else {
                rightButtonBadge.bindTarget(btnRight);
                rightButtonBadge.setBadgeGravity(Gravity.TOP | Gravity.END);
                rightButtonBadge.setBadgeNumber(rightBadge);
                rightButtonBadge.setBadgeTextSize(10, true);
                rightButtonBadge.setGravityOffset(-2, true);
            }
        }
        return this;
    }

    public MFTitleBar hideBottomLine() {
        if (showBottomLine) {
            removeView(viewBottomLine);
        }
        return this;
    }
}
