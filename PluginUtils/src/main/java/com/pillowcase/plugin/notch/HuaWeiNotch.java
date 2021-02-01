package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.pillowcase.plugin.utils.PluginLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： 华为
 */
public class HuaWeiNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            ClassLoader cl = activity.getClassLoader();
            Class<?> HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            return (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    @Override
    public boolean FitNotch(Window window) {
        try {
            WindowManager.LayoutParams attributes = window.getAttributes();
            Class<?> layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor<?> con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(attributes);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
            return true;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
