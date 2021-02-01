package com.pillowcase.plugin.notch;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.pillowcase.plugin.interfaces.INotchCallback;
import com.pillowcase.plugin.utils.PluginLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 13:58
 * Description ： Api
 */
public class ApiNotch extends SimpleNotch {
    private INotchCallback mCallback;

    @Override
    public boolean isNotch(Activity activity) {
        try {

        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    public void isNotch(Activity activity, INotchCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            final View decorView = activity.getWindow().getDecorView();
            decorView.post(() -> {
                try {
                    Method method = View.class.getDeclaredMethod("getRootWindowInsets");
                    WindowInsets windowInsets = (WindowInsets) method.invoke(decorView);

                    if (windowInsets != null) {
                        method = WindowInsets.class.getDeclaredMethod("getDisplayCutout");
                        Object displayCutoutInstance = method.invoke(windowInsets);
                        PluginLog.log("Operation ==> displayCutoutInstance : " + displayCutoutInstance);

                        if (displayCutoutInstance != null) {
                            Rect safeInsets = new Rect();
                            Class cls = displayCutoutInstance.getClass();
                            int top = (int) cls.getDeclaredMethod("getSafeInsetTop").invoke
                                    (displayCutoutInstance);
                            int bottom = (int) cls.getDeclaredMethod("getSafeInsetBottom").invoke
                                    (displayCutoutInstance);
                            int left = (int) cls.getDeclaredMethod("getSafeInsetLeft").invoke
                                    (displayCutoutInstance);
                            int right = (int) cls.getDeclaredMethod("getSafeInsetRight").invoke
                                    (displayCutoutInstance);
                            safeInsets.set(left, top, right, bottom);
                            List<Rect> boundingReacts = new ArrayList<>((List<Rect>) cls.getDeclaredMethod("getBoundingRects").invoke(displayCutoutInstance));

                            if (boundingReacts.size() > 0) {
                                callback.isNotch(true);
                            } else {
                                callback.isNotch(false);
                            }
                        }
                    }
                } catch (Exception e) {
                    PluginLog.error(e);
                    callback.isNotch(false);
                }
            });
        } else {
            callback.isNotch(false);
        }
    }

    @Override
    public boolean FitNotch(Window window) {
        try {
            WindowManager.LayoutParams attributes = window.getAttributes();
            //layoutInDisplayCutoutMode int.class
            Field field = attributes.getClass().getDeclaredField("layoutInDisplayCutoutMode");
            field.set(attributes, LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            window.setAttributes(attributes);

            if (field.get(attributes) instanceof Integer) {
                int data = (int) field.get(attributes);
                return data != LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            }
            return true;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
