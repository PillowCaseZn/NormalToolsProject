package com.pillowcase.identifier;

import android.app.Application;
import android.content.Context;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-13 11:43
 * Description :
 */
public class UniqueIdentifierApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        UniqueIdentifierUtils.getInstance().loadLibrary(base);
    }
}
