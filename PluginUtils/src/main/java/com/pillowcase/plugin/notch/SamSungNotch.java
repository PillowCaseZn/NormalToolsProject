package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Window;

import com.pillowcase.plugin.utils.PluginLog;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： 三星
 */
public class SamSungNotch extends SimpleNotch {
    @Override
    public boolean isNotch(Activity activity) {
        try {
            Resources resources = activity.getResources();
            int resId = resources.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            String spec = resId > 0 ? resources.getString(resId) : null;
            return spec != null && !TextUtils.isEmpty(spec);
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
