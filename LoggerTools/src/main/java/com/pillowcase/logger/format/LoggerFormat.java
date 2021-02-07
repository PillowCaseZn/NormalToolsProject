package com.pillowcase.logger.format;

import com.pillowcase.logger.module.PillowLoggerBorder;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 18:13
 * Description ：
 */
public  abstract class LoggerFormat {
    protected static final String DOUBLE_DIVIDER = PillowLoggerBorder.DOUBLE_DIVIDER;

    protected static final String LINE_SEPARATOR = PillowLoggerBorder.LINE_SEPARATOR;
    protected static final String DATA_SEPARATOR = PillowLoggerBorder.DATA_SEPARATOR;

    protected static final String CONTENT_START_BORDER = PillowLoggerBorder.CONTENT_START_BORDER;

    public static StringBuilder singleLineMaxFormat(String message, String separator, int maxIndex) {
        StringBuilder builder = new StringBuilder();
        while (message.length() > PillowLoggerBorder.DOUBLE_DIVIDER.length()) {
            builder.append(message.substring(0, maxIndex)).append(PillowLoggerBorder.LINE_SEPARATOR);
            message = separator + message.substring(maxIndex);
        }
        if (message.length() > 0) {
            builder.append(message);
        }
        return builder;
    }
}
