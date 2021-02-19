package com.pillowcase.demo;

import android.app.Application;

import com.pillowcase.logger.LoggerUtils;

/**
 * Author      : PillowCase
 * Create On   : 2020-07-29 15:35
 * Description :
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtils.getInstance().crash(this);
    }
}
