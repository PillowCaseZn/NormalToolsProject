package com.pillowcase.normal.tools.logger.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 15:02
 * Description :
 */
public interface FormatStrategy {
    void log(int priority, @Nullable String tag, @NonNull String message);
}
