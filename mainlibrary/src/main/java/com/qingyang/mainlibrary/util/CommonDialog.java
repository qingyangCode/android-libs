package com.qingyang.mainlibrary.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.qingyang.mainlibrary.R;

public class CommonDialog extends Dialog {

    private TextView mTvTitle, mTvContent;
    private ImageView mImageview;
    private Button mButtonTop;
    private Button mButtonBottom;

    public CommonDialog(Context context) {
        super(context, R.style.theme_common_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.common_dialog, null);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mButtonTop = (Button) view.findViewById(R.id.btn_top);
        mButtonBottom = (Button) view.findViewById(R.id.btn_bottom);
        mImageview =  (ImageView)view.findViewById(R.id.iv_icon);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public void setButtonStyle1() {
        mButtonTop.setBackgroundResource(R.drawable.common_dialog_button_bg_1);
        mButtonBottom.setBackgroundResource(R.drawable.common_dialog_button_bg_2);
    }

    public void setButtonStyle2() {
        //mButtonTop.setBackgroundResource(R.drawable.common_dialog_button_bg_3);
        //mButtonBottom.setBackgroundResource(R.drawable.common_dialog_button_bg_4);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(int resId) {
        mTvTitle.setText(resId);
    }

    public void setContent(CharSequence content) {
        mTvContent.setText(content);
    }

    public void setContent(int resId) {
        mTvContent.setText(resId);
    }

    public void setButtonTopText(int resId) {
        mButtonTop.setText(resId);
    }

    public void setButtonBottomText(int resId) {
        mButtonBottom.setText(resId);
    }

    public void setButtonTopText(String text) {
        mButtonTop.setText(text);
    }

    public void setButtonBottomText(String text) {
        mButtonBottom.setText(text);
    }

    public TextView getmTvContent() {
        return mTvContent;
    }

    public void setOnClickButtonTopListener(View.OnClickListener listener) {
        mButtonTop.setOnClickListener(listener);
    }

    public void setOnClickButtonBottomListener(View.OnClickListener listener) {
        mButtonBottom.setOnClickListener(listener);
    }

    public  void setImageview(int resId){
        this.mImageview.setVisibility(View.VISIBLE);
        this.mImageview.setImageResource(resId);
    }

    public Button getmButtonTop() {
        return mButtonTop;
    }

    public void setmButtonTop(Button mButtonTop) {
        this.mButtonTop = mButtonTop;
    }

    public Button getmButtonBottom() {
        return mButtonBottom;
    }

    public void setmButtonBottom(Button mButtonBottom) {
        this.mButtonBottom = mButtonBottom;
    }
    public void hiddeTopButton(){
        mButtonTop.setVisibility(View.GONE);
    }
}
