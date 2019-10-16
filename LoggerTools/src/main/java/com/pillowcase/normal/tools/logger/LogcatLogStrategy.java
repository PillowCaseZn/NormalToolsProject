package com.pillowcase.normal.tools.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pillowcase.normal.tools.logger.impl.LogStrategy;

import static com.pillowcase.normal.tools.logger.Utils.checkNotNull;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 15:04
 * Description :
 */
public class LogcatLogStrategy implements LogStrategy {
    static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        checkNotNull(message);

        if (tag == null) {
            tag = DEFAULT_TAG;
        }

        Log.println(priority, tag, message);
    }
}
