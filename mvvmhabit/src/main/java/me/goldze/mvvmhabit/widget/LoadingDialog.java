package me.goldze.mvvmhabit.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import me.goldze.mvvmhabit.R;

/**
 * Created by Suuu on 2022/10/25.
 * 加载动画弹窗
 */
public class LoadingDialog extends Dialog {
    private Context context;
    private TextView tvProgress;
    private TextView tvTitle;
    private String title;
    private View inflate;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public LoadingDialog(@NonNull Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        setContentView(inflate);
        initView();
    }

    private void initView() {
        tvProgress = inflate.findViewById(R.id.tv_progress);
        tvTitle = inflate.findViewById(R.id.tv_title);
        setTitle(title);

        Window window = getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        //点击Dialog外部不可消失
        setCanceledOnTouchOutside(false);
        //点击返回键不可消失
        setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    public void setProgress(int progress) {
        if (progress > -1) {
            tvProgress.setVisibility(View.VISIBLE);
            tvProgress.setText(progress + "%");
            if (progress == 100) {
                tvProgress.setVisibility(View.GONE);
            }
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        tvProgress.setText("0%");
    }
}
