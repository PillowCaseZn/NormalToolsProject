package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.view.Window;

import com.pillowcase.plugin.utils.PluginLog;

import java.lang.reflect.Field;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： 魅族
 */
public class FlyMeNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            Class clazz = Class.forName("flyme.config.FlymeFeature");
            Field field = clazz.getDeclaredField("IS_FRINGE_DEVICE");
            return  (boolean) field.get(null);
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
