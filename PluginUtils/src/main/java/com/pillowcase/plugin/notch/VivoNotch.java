package com.pillowcase.plugin.notch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Window;

import com.pillowcase.plugin.utils.PluginLog;

import java.lang.reflect.Method;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： vivo
 */
public class VivoNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            ClassLoader cl = activity.getClassLoader();
            @SuppressLint("PrivateApi") Class<?> FtFeature = cl.loadClass("android.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport", int.class);
            return (boolean) get.invoke(FtFeature, 0x00000020);
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    @Override
    public boolean FitNotch(Window window) {
        try {

            return true;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
