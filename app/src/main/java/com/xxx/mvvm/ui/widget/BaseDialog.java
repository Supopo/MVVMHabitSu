package com.xxx.mvvm.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xxx.mvvm.R;

/**
 * Created by Suuu on 2022/10/19.
 * 底部弹窗
 */
public class BaseDialog extends Dialog implements View.OnClickListener {
    private View view;
    private int layoutRes;
    private int[] clickIds;   //需要设置点击事件的ID.需要其他ID,在dialog实例化后在dialog上fbc.
    private int height;
    private int width;
    private int gravity;

    public BaseDialog(Context context, int layoutRes, int[] clickIds) {
        super(context, R.style.DialogStyle);    //设置主题
        this.layoutRes = layoutRes;
        this.clickIds = clickIds;
    }

    public BaseDialog(Context context, View view, int[] clickIds) {
        super(context, R.style.DialogStyle);    //设置主题
        this.view = view;
        this.clickIds = clickIds;
    }

    public BaseDialog(Context context, int layoutRes, int[] clickIds, int gravity) {
        super(context, R.style.DialogStyle);    //设置主题
        this.layoutRes = layoutRes;
        this.clickIds = clickIds;
        this.gravity = gravity;
    }

    public BaseDialog(Context context, View view, int[] clickIds, int gravity) {
        super(context, R.style.DialogStyle);    //设置主题
        this.view = view;
        this.clickIds = clickIds;
        this.gravity = gravity;
    }

    public BaseDialog(Context context, int layoutRes, int[] clickIds, int width, int height, int gravity) {
        super(context, R.style.DialogStyle);    //设置主题
        this.layoutRes = layoutRes;
        this.clickIds = clickIds;
        this.width = width;
        this.height = height;
        this.gravity = gravity;
    }

    public BaseDialog(Context context, View view, int[] clickIds, int width, int height, int gravity) {
        super(context, R.style.DialogStyle);    //设置主题
        this.view = view;
        this.clickIds = clickIds;
        this.width = width;
        this.height = height;
        this.gravity = gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置动画
        window.setWindowAnimations(R.style.DialogBottomAnimation);

        if (view != null) {
            setContentView(view);
        } else {
            setContentView(layoutRes);
        }

        if (gravity != 0) {
            window.setGravity(gravity);
        } else {
            window.setGravity(Gravity.CENTER);
        }

        if (width > 0 && height > 0) {
            getWindow().setLayout(width, height);
        } else {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        //点击Dialog外部不可消失
        setCanceledOnTouchOutside(false);
        //点击返回键不可消失
        setCancelable(false);

        //设置点击事件
        if (clickIds != null) {
            for (int id : clickIds) {
                findViewById(id).setOnClickListener(this);
            }
        }
    }

    public View getView() {
        if (view == null) {
            return getLayoutInflater().inflate(layoutRes, null);
        }
        return view;
    }

    private OnBottomItemClickListener listener;

    public interface OnBottomItemClickListener {
        void onBottomItemClick(BaseDialog dialog, View view);
    }

    public void setOnBottomItemClickListener(OnBottomItemClickListener listener) {
        if (this.listener == null)
            this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (this.listener != null)
            listener.onBottomItemClick(this, v);
    }
}
