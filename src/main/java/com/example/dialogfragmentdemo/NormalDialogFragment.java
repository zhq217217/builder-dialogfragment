package com.example.dialogfragmentdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
/**
 * Created by Administrator on 2017/5/9.
 */

/**
 *
 */
public class NormalDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String TYPE = "type";
    private static final String POSITIVE_TEXT = "positive_text";
    private static final String NEGATIVE_TEXT = "negative_text";
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_NO_CONTENT = 1;
    private static final int TYPE_CUSTOM = 2;
    /**
     * 类型，默认为有标题 有内容 有确认取消按钮
     */
    private int mType = 0;
    private TextView mTvTitle, mTvContent, mTvPositive, mTvNegative;
    private View mDivideView;

    private String mTitle, mContent, mNegativeText, mPositiveText;

    /**
     * 有标题有内容
     *
     * @param title
     * @param content
     * @return
     */
    public static NormalDialogFragment getInstance(String title, String content) {
        NormalDialogFragment normalFragmentDialog = new NormalDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);
        bundle.putInt(TYPE, TYPE_DEFAULT);
        normalFragmentDialog.setArguments(bundle);
        return normalFragmentDialog;
    }

    /**
     * 有标题 无内容 自定义确认取消
     *
     * @param title
     * @param positiveText
     * @param NegitiveText
     * @return
     */
    public static NormalDialogFragment getInstance(String title, String content, String positiveText, String NegitiveText) {
        NormalDialogFragment normalFragmentDialog = new NormalDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);
        bundle.putString(POSITIVE_TEXT, positiveText);
        bundle.putString(NEGATIVE_TEXT, NegitiveText);
        bundle.putInt(TYPE, TYPE_CUSTOM);
        normalFragmentDialog.setArguments(bundle);
        return normalFragmentDialog;
    }

    /**
     * 有标题 无内容 自定义确认取消
     *
     * @param title
     * @param positiveText
     * @param NegitiveText
     * @return
     */
    public static NormalDialogFragment getInstance(String title, String positiveText, String NegitiveText) {
        NormalDialogFragment normalFragmentDialog = new NormalDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(POSITIVE_TEXT, positiveText);
        bundle.putString(NEGATIVE_TEXT, NegitiveText);
        bundle.putInt(TYPE, TYPE_NO_CONTENT);
        normalFragmentDialog.setArguments(bundle);
        return normalFragmentDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mType = arguments.getInt(TYPE);
        switch (mType) {
            case TYPE_NO_CONTENT:
                mTitle = arguments.getString(TITLE);
                mPositiveText = arguments.getString(POSITIVE_TEXT);
                mNegativeText = arguments.getString(NEGATIVE_TEXT);
                break;
            case TYPE_CUSTOM:
                mTitle = arguments.getString(TITLE);
                mContent = arguments.getString(CONTENT);
                mPositiveText = arguments.getString(POSITIVE_TEXT);
                mNegativeText = arguments.getString(NEGATIVE_TEXT);
                break;
            default:
                mTitle = arguments.getString(TITLE);
                mContent = arguments.getString(CONTENT);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflate = inflater.inflate(R.layout.fragment_normal_dialog, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvNegative = (TextView) view.findViewById(R.id.tv_negativate);
        mTvPositive = (TextView) view.findViewById(R.id.tv_positive);
        mDivideView = view.findViewById(R.id.divide_view);
        mTvPositive.setOnClickListener(this);
        mTvNegative.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switch (mType) {
            case TYPE_NO_CONTENT:
                mTvTitle.setText(mTitle);
                mTvTitle.setPadding(0, 0, 0, 80);
                mTvContent.setVisibility(View.GONE);
                mTvPositive.setText(mPositiveText);
                mTvNegative.setText(mNegativeText);
                break;
            case TYPE_CUSTOM:
                mTvTitle.setText(mTitle);
                mTvTitle.setPadding(0, 0, 0, 0);
                mTvContent.setText(mContent);
                mTvPositive.setText(mPositiveText);
                mTvNegative.setText(mNegativeText);
                break;
            default:
                mTvTitle.setText(mTitle);
                mTvContent.setText(mContent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_positive:
                this.dismissAllowingStateLoss();
                if (null != onPositiveClickListener) {
                    onPositiveClickListener.onPositiveClickListen();
                }
                break;
            case R.id.tv_negativate:
                this.dismissAllowingStateLoss();
                if (null != onNegativeClickListener) {
                    onNegativeClickListener.onNegativeClickListen();
                }
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (null != onDismissListener) {
            onDismissListener.onDismissListener();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (this.isAdded()) {
            this.dismiss();
        } else {
            manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    public interface OnNegativeClickListener {
        void onNegativeClickListen();
    }

    private OnNegativeClickListener onNegativeClickListener;

    public void setOnNegativeClickListener(OnNegativeClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
    }

    public interface OnPositiveClickListener {
        void onPositiveClickListen();
    }

    private OnPositiveClickListener onPositiveClickListener;

    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    public interface OnDismissListener {
        void onDismissListener();
    }

    private OnDismissListener onDismissListener;

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }
}
