package com.pillowcase.logger.format;

import android.text.TextUtils;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:12
 * Description ：
 */
public class StringFormat extends LoggerFormat {

    public static String format(String message) {
        return format(message, true);
    }

    /**
     * @param message 字符串
     * @return 格式化字符串
     */
    public static String format(String message, boolean isSecond) {
        StringBuilder builder = new StringBuilder();

        String[] split = null;
        if (message.contains("\n")) {
            split = message.split("\n");
        } else if (message.contains(",")) {
            split = message.split(",");
        }
        if (split != null) {
            for (int i = 0; i < split.length; i++) {
                String line = split[i];
                if (line.equals("") || TextUtils.isEmpty(line)) {
                    continue;
                }

                int maxIndex = (i == 0 || i == split.length - 1) ? 1 : 2;
                maxIndex = maxIndex == 1 ? DOUBLE_DIVIDER.length() - 3 : DOUBLE_DIVIDER.length() - 6;
                String separator = (i == 0 || i == split.length - 1) ? CONTENT_START_BORDER : CONTENT_START_BORDER + DATA_SEPARATOR;
                if (isSecond) {
                    separator += DATA_SEPARATOR;
                }

                line = separator + line;

                builder.append(singleLineMaxFormat(line, separator, maxIndex)).append(LINE_SEPARATOR);
            }
        }
        return builder.toString();
    }
}
