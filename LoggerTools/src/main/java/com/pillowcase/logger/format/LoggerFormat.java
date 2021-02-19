package com.pillowcase.logger.format;

import com.pillowcase.logger.module.LoggerBorder;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 18:13
 * Description ：
 */
public  abstract class LoggerFormat {
    protected static final String DOUBLE_DIVIDER = LoggerBorder.DOUBLE_DIVIDER;

    protected static final String LINE_SEPARATOR = LoggerBorder.LINE_SEPARATOR;
    protected static final String DATA_SEPARATOR = LoggerBorder.DATA_SEPARATOR;

    protected static final String CONTENT_START_BORDER = LoggerBorder.CONTENT_START_BORDER;

    public static StringBuilder singleLineMaxFormat(String message, String separator, int maxIndex) {
        StringBuilder builder = new StringBuilder();
        while (message.length() > LoggerBorder.DOUBLE_DIVIDER.length()) {
            builder.append(message.substring(0, maxIndex)).append(LoggerBorder.LINE_SEPARATOR);
            message = separator + message.substring(maxIndex);
        }
        if (message.length() > 0) {
            builder.append(message);
        }
        return builder;
    }
}
