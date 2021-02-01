package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.view.Window;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 11:54
 * Description ：
 */
public abstract class SimpleNotch {
    /*刘海屏全屏显示FLAG*/
    protected static final int FLAG_NOTCH_SUPPORT = 0x00000100; // 开启配置
    protected static final int FLAG_NOTCH_PORTRAIT = 0x00000200; // 竖屏配置
    protected static final int FLAG_NOTCH_HORIZONTAL = 0x00000400; // 横屏配置

    //默认情况,全屏页面不可用刘海区域,非全屏页面可以进行使用
    protected static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
    //不允许使用刘海区域
    protected static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;
    //允许页面延伸到刘海区域
    protected static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;

    /**
     * @param activity 上下文
     * @return 是否非全面屏
     */
    public abstract boolean isNotch(Activity activity);

    /**
     * 适配
     */
    public abstract boolean FitNotch(Window window);
}
