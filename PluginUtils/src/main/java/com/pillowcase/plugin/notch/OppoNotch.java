package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.view.Window;

import com.pillowcase.plugin.utils.PluginLog;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： Oppo
 */
public class OppoNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            return activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
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
