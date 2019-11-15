package com.pillowcase.normal.tools.only.sign;

import android.app.Application;
import android.content.Context;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-13 11:43
 * Description :
 */
public class OnlySignApplicatioon extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        OnlySignUtils.getInstance().loadLibrary(base);
    }
}
