package com.example.dialogfragmentdemo;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhq on 2017/12/1.
 */

public class CustomDialogFragment extends DialogFragment {
    public static final int NO_SET_DATA = -1;
    public static final String LAYOUT_RES_ID = "res_id";
    public static final String CUSTOME_STYLE = "style";
    public static final String DIALOG_WIDTH = "width";
    public static final String DIALOG_HEIGHT = "height";
    public static final String DIALOG_GRAVITY = "gravity";
    public static final String DIALOG_DIMAMOUNT = "amount";
    public static final String DIALOG_NO_TITLE = "no_title";
    public static final String DIALOG_GET_ROOTVIEW = "no_title";
    public static final String DIALOG_STATE_BAR_TRANSLATE = "bar_translate";
    public static final String DIALOG_CANCLEABLE = "cancle";
    public static final String DIALOG_CANCLEABLE_OUTSIDE = "cancle_outside";
    public static final String DIALOG_ANIMATION = "animation";


    public static CustomDialogFragment getInstance(int resId, int styleId, int width, int height, int gravity,
                                                   float dimamount, boolean notitle, boolean stateBarTranslate, boolean cancleAble,
                                                   boolean canceledOnTouchOutside, int animationId, Builder.OnRootViewReadyListener listener) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_RES_ID, resId);
        bundle.putInt(CUSTOME_STYLE, styleId);
        bundle.putInt(DIALOG_WIDTH, width);
        bundle.putInt(DIALOG_HEIGHT, height);
        bundle.putInt(DIALOG_GRAVITY, gravity);
        bundle.putFloat(DIALOG_DIMAMOUNT, dimamount);
        bundle.putBoolean(DIALOG_NO_TITLE, notitle);
        bundle.putParcelable(DIALOG_GET_ROOTVIEW, listener);
        bundle.putBoolean(DIALOG_STATE_BAR_TRANSLATE, stateBarTranslate);
        bundle.putBoolean(DIALOG_CANCLEABLE, cancleAble);
        bundle.putBoolean(DIALOG_CANCLEABLE_OUTSIDE, canceledOnTouchOutside);
        bundle.putInt(DIALOG_ANIMATION, animationId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int styleId = getArguments().getInt(CUSTOME_STYLE, -1);
        if (NO_SET_DATA != styleId) {
            setStyle(DialogFragment.STYLE_NORMAL, styleId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        Bundle bundle = getArguments();
        if (bundle.getBoolean(DIALOG_NO_TITLE)) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        float dimamount = bundle.getFloat(DIALOG_DIMAMOUNT);
        if (-1.0f != dimamount) {
            getDialog().getWindow().setDimAmount(dimamount);//设置背景颜色
        }
        if (bundle.getBoolean(DIALOG_STATE_BAR_TRANSLATE, false)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        if (NO_SET_DATA != bundle.getInt(DIALOG_ANIMATION)) {
            getDialog().getWindow().setWindowAnimations(bundle.getInt(DIALOG_ANIMATION));
        }
        return inflater.inflate(getArguments().getInt(LAYOUT_RES_ID), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Builder.OnRootViewReadyListener listener = getArguments().getParcelable(DIALOG_GET_ROOTVIEW);
        if (null != listener) {
            listener.rootView(view, savedInstanceState, this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        int width = arguments.getInt(DIALOG_WIDTH);
        int height = arguments.getInt(DIALOG_HEIGHT);
        int gravity = arguments.getInt(DIALOG_GRAVITY);
        if (-3 != (width + height + gravity)) {
            Window dialogWindow = getDialog().getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            if (NO_SET_DATA != width) {
                lp.width = width;
            }
            if (NO_SET_DATA != height) {
                lp.height = height;
            }
            if (NO_SET_DATA != gravity) {
                lp.gravity = gravity;
            }
            dialogWindow.setAttributes(lp);
        }
        getDialog().setCancelable(arguments.getBoolean(DIALOG_CANCLEABLE));
        getDialog().setCanceledOnTouchOutside(arguments.getBoolean(DIALOG_CANCLEABLE_OUTSIDE));
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!this.isAdded()) {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    static class Builder {
        /**
         * 设置页面布局文件id
         */
        private int resId;
        /**
         * 设置显示样式，可以在样式中设置动画等等
         */
        private int styleId = -1;
        /**
         * 设置高度
         */
        private int height = -1;
        /**
         * 设置宽度
         */
        private int width = -1;
        /**
         * 设置显示的位置
         */
        private int gravity = -1;
        /**
         * 设置背景透明度
         */
        private float dimAmount = 0.6f;
        /**
         * 是否为没有标题
         */
        private boolean notitile = true;
        /**
         * 处理4.4以上手机全屏时状态栏变黑
         */
        private boolean stateBarTranslate = true;
        /**
         * 点击返回键是否消失
         */
        private boolean cancleAble = true;
        /**
         * 点击返回键是否消失
         */
        private int animationId = -1;
        /**
         * 触摸弹框外部区域是否消失
         */
        private boolean canceledOnTouchOutside = true;
        /**
         * 设置消失事件监听
         */
        private OnDilogDisssmissListener dissListener;
        /**
         * 拿到页面根view
         */
        private OnRootViewReadyListener rootViewReadyListener;

        static Builder getInstance() {
            return new Builder();
        }

        void show(FragmentManager fragmentManager) {
            CustomDialogFragment dialogFragment = CustomDialogFragment.getInstance(resId, styleId, width, height, gravity, dimAmount,
                    notitile, stateBarTranslate, cancleAble, canceledOnTouchOutside, animationId, rootViewReadyListener);
            if (null != dissListener) {
                dialogFragment.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dissListener.onDissmisss();
                    }
                });
            }
            dialogFragment.show(fragmentManager, "");
        }

        Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        Builder setAnimationId(int animationId) {
            this.animationId = animationId;
            return this;
        }

        Builder setStyleId(int styleId) {
            this.styleId = styleId;
            return this;
        }

        Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        Builder setNoTitle(boolean notitle) {
            this.notitile = notitle;
            return this;
        }

        Builder setOnDilogDisssmissListener(OnDilogDisssmissListener listener) {
            this.dissListener = listener;
            return this;
        }

        Builder setOnRootViewReadyListener(OnRootViewReadyListener listener) {
            this.rootViewReadyListener = listener;
            return this;
        }

        Builder setStateBarTranslate(boolean stateBarTranslate) {
            this.stateBarTranslate = stateBarTranslate;
            return this;
        }

        Builder setCancelable(boolean cancleAble) {
            this.cancleAble = cancleAble;
            return this;
        }

        Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        interface OnDilogDisssmissListener {
            void onDissmisss();
        }

        static abstract class OnRootViewReadyListener implements Parcelable {
            abstract void rootView(View view, Bundle savedInstanceState, DialogFragment dialog);

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
            }
        }
    }
}
