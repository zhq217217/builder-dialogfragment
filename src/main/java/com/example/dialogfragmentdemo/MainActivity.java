package com.example.dialogfragmentdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * 显示最普通的一个dialog
     *
     * @param view
     */
    public void showNormalDialog(View view) {
        NormalFragmentDialog.getInstance().show(getFragmentManager(), "");
    }

    public static class NormalFragmentDialog extends DialogFragment {
        public static NormalFragmentDialog getInstance() {
            NormalFragmentDialog normalFragmentDialog = new NormalFragmentDialog();
            return normalFragmentDialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle("普通的DIALOG")
                    .setMessage("这是一个普通的dialog")
                    .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            return alertDialog;
        }
    }


    /**
     * 自定义布局的dialog(很丑是不是)
     *
     * @param view
     */
    public void customViewDialog(View view) {
        CustomViewDialg.getInstance(false).show(getFragmentManager(), "");
    }

    public static class CustomViewDialg extends DialogFragment implements View.OnClickListener {
        private static final String NO_TITLE = "no_title";
        private boolean mNoTitle;

        /**
         * @param noTitle 是否含有title
         * @return
         */
        public static CustomViewDialg getInstance(boolean noTitle) {
            CustomViewDialg customViewDialg = new CustomViewDialg();
            Bundle bundle = new Bundle();
            bundle.putBoolean(NO_TITLE, noTitle);
            customViewDialg.setArguments(bundle);
            return customViewDialg;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNoTitle = getArguments().getBoolean(NO_TITLE);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (mNoTitle) {
                getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//设置dialog没有title
            }
            return inflater.inflate(R.layout.layout_custom_dialog, container, false);
            //这里需要说明一下，这个view的高度，由布局的所有控件高度与padding和margin的高度和
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            view.findViewById(R.id.tv_cancle).setOnClickListener(this);
            view.findViewById(R.id.tv_ok).setOnClickListener(this);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancle:
                    this.dismiss();
                    break;
                case R.id.tv_ok:
                    this.dismiss();
                    break;
            }
        }
    }


    /**
     * 没有title的自定义布局dialog，是不是很好
     */
    public void customViewDialogNotitle(View view) {
        CustomViewDialg.getInstance(true).show(getFragmentManager(), "");
    }


    /**
     * 改变大小
     *
     * @param view
     */
    public void customViewDialogChangeSize(View view) {
        CustomViewDialgChangeSize.getInstance().show(getFragmentManager(), "");
    }

    public static class CustomViewDialgChangeSize extends DialogFragment implements View.OnClickListener {


        public static CustomViewDialgChangeSize getInstance() {
            CustomViewDialgChangeSize customViewDialg = new CustomViewDialgChangeSize();
            return customViewDialg;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            return inflater.inflate(R.layout.layout_custom_dialog, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            view.findViewById(R.id.tv_cancle).setOnClickListener(this);
            view.findViewById(R.id.tv_ok).setOnClickListener(this);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            //注意所有方法在哪个生命周期中设置
            Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
            Window dialogWindow = getDialog().getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = defaultDisplay.getWidth() - 200;//两边设置的间隙相当于margin,改变大小
            dialogWindow.setAttributes(lp);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancle:
                    this.dismiss();
                    break;
                case R.id.tv_ok:
                    this.dismiss();
                    break;
            }
        }
    }


    /**
     * 好像没有改变
     *
     * @param view
     */
    public void dialogChangeStyle(View view) {
        NormalChangeStyleDialog.getInstance().show(getFragmentManager(), "");
    }

    public static class NormalChangeStyleDialog extends DialogFragment {
        public static NormalChangeStyleDialog getInstance() {
            NormalChangeStyleDialog normalFragmentDialog = new NormalChangeStyleDialog();
            return normalFragmentDialog;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("这是一个改变了样式的普通的dialog")
                    .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
    }


    /**
     * 可以在屏幕的任意位置，改变任意大小
     *
     * @param view
     */
    public void customViewAnyPositionDialog(View view) {
//        CustomViewAnyPositionDialog.getInstance().show(getFragmentManager(), "");
        CustomDialogFragment.Builder.getInstance()
                .setResId(R.layout.layout_custom_dialog1)
                .setStyleId(R.style.MyDialog1)
                .setStateBarTranslate(false)
                .setGravity(Gravity.LEFT | Gravity.TOP)
                .show(getFragmentManager());
    }

    public static class CustomViewAnyPositionDialog extends DialogFragment {
        public static CustomViewAnyPositionDialog getInstance() {
            CustomViewAnyPositionDialog customViewAnyPositionDialog = new CustomViewAnyPositionDialog();
            return customViewAnyPositionDialog;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //1 通过样式定义,DialogFragment.STYLE_NORMAL这个很关键的
            setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog1);
            //2代码设置 无标题 无边框  这个就很坑爹，这么设置很多系统效果就都没有了
            //setStyle(DialogFragment.STYLE_NO_TITLE|DialogFragment.STYLE_NO_FRAME,0);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //3 在此处设置 无标题 对话框背景色
            //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            // dialog的背景色
//            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
            //getDialog().getWindow().setDimAmount(0.8f);//背景黑暗度
            return inflater.inflate(R.layout.layout_custom_dialog1, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
            Window dialogWindow = getDialog().getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.gravity = Gravity.LEFT | Gravity.TOP;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
            dialogWindow.setAttributes(lp);

            getDialog().setCancelable(false);
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                    dialog.dismiss();
                    return false;
                }
            });
        }
    }


    /**
     * 最后一个带动画效果的自定义view的dialog
     *
     * @param view
     */
    public void customAnimationDialog(View view) {
//        CustomAnimationDialg.getInstance().show(getFragmentManager(), "");
        CustomDialogFragment.Builder.getInstance()
                .setResId(R.layout.layout_custom_dialog2)
                .setStyleId(R.style.CustomDatePickerDialog)
                .setWidth(getWindow().getWindowManager().getDefaultDisplay().getWidth())
                .setGravity(Gravity.BOTTOM)
                .show(getFragmentManager());
    }

    public static class CustomAnimationDialg extends DialogFragment {
        public static CustomAnimationDialg getInstance() {
            return new CustomAnimationDialg();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDatePickerDialog);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.layout_custom_dialog2, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Window dialogWindow = getDialog().getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.BOTTOM;
            dialogWindow.setAttributes(lp);
        }
    }

    public void mycus(View view) {
        CustomDialogFragment.Builder.getInstance()
                .setResId(R.layout.layout_one)
                .setAnimationId(R.style.dialog_animation)
                .setOnRootViewReadyListener(new CustomDialogFragment.Builder.OnRootViewReadyListener() {
                    @Override
                    void rootView(View view, Bundle savedInstanceState, final DialogFragment dialog) {
                        view.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        view.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .show(getFragmentManager());
    }

    public void exit(View view) {
        NormalDialogFragment instance = NormalDialogFragment.getInstance("确定退出吗?", "确定", "取消");
        instance.setOnPositiveClickListener(new NormalDialogFragment.OnPositiveClickListener() {
            @Override
            public void onPositiveClickListen() {
                //退出
            }
        });
        instance.show(getSupportFragmentManager(), "");
    }

    public void showcontent(View view) {
        NormalDialogFragment instance = NormalDialogFragment.getInstance("确定退出吗?", "退出后将丢失重要文件");
        instance.setOnPositiveClickListener(new NormalDialogFragment.OnPositiveClickListener() {
            @Override
            public void onPositiveClickListen() {
                //退出
            }
        });
        instance.show(getSupportFragmentManager(), "");
    }
}
