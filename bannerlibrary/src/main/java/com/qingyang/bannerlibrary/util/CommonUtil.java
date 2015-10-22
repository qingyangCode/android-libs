package com.qingyang.bannerlibrary.util;

import android.content.Context;

/**
 * Created by QingYang on 15/9/8.
 */
public class CommonUtil {

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
