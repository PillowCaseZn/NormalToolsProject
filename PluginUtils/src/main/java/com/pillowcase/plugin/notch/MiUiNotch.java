package com.pillowcase.plugin.notch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Window;

import com.pillowcase.plugin.utils.PluginLog;

import java.lang.reflect.Method;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： 小米
 */
public class MiUiNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            ClassLoader cl = activity.getClassLoader();
            @SuppressLint("PrivateApi") Class<?> SystemProperties = cl.loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("getInt", String.class, int.class);
            return (Integer) get.invoke(SystemProperties, "ro.miui.notch", 0) == 1;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    @Override
    public boolean FitNotch(Window window) {
        try {
            Method method = Window.class.getMethod("addExtraFlags", int.class);
            method.invoke(window, FLAG_NOTCH_SUPPORT | FLAG_NOTCH_HORIZONTAL);
            return true;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
