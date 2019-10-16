package com.pillowcase.normal.tools.logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pillowcase.normal.tools.logger.impl.FormatStrategy;
import com.pillowcase.normal.tools.logger.impl.LogAdapter;

import static com.pillowcase.normal.tools.logger.Utils.checkNotNull;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 15:01
 * Description :
 */
public class AndroidLogAdapter implements LogAdapter {
    @NonNull
    private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
