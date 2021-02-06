package com.pillowcase.logger;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-06 00:18
 * Description ： Log 日志输出、捕获，输出到LogCat
 */
public class PillowLogger {
    private static PillowLogger instance;
    private Logger mLogger;
    private Level mLevel;


    private final char TOP_LEFT_CORNER = '╔';
    private final char MIDDLE_CORNER = '╟';
    private final char BOTTOM_LEFT_CORNER = '╚';
    private final char HORIZONTAL_DOUBLE_LINE = '║';

    private final String DOUBLE_DIVIDER = "══════════════════════════════════════════════════════════════════════════════════";
    private final String SINGLE_DIVIDER = "──────────────────────────────────────────────────────────────────────────────────";

    private final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final String DATA_SEPARATOR = "\t";

    private final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + LINE_SEPARATOR;
    private final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + LINE_SEPARATOR;
    private final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER;
    private final String CONTENT_START_BORDER = HORIZONTAL_DOUBLE_LINE + DATA_SEPARATOR;

    private PillowLogger() {
        if (mLogger == null) {
            mLogger = Logger.getLogger("Logger");
        }
    }

    public static PillowLogger getInstance() {
        if (instance == null) {
            synchronized (PillowLogger.class) {
                if (instance == null) {
                    instance = new PillowLogger();
                }
            }
        }
        return instance;
    }

    public void log(Object o) {
        log("", o);
    }

    public void error(Exception exception) {
        error("", exception);
    }

    public void log(String tag, Object o) {
        mLevel = Level.INFO;
        printLogger(tag, o);
    }

    public void error(String tag, Exception exception) {
        mLevel = Level.WARNING;
        printLogger(tag, exception);
    }

    private void printLogger(String tag, Object object) {
        StringBuilder builder = new StringBuilder();
        builder.append(printHeaderLine(tag));

        if (object == null) {
            builder.append(BOTTOM_BORDER);
        } else {
            builder.append(printContentLine(object))
                    .append(LINE_SEPARATOR)
                    .append(BOTTOM_BORDER);
        }
        printLogCat(builder.toString());
    }

    /**
     * 输出日志到LogCat
     *
     * @param message 日志信息
     */
    private void printLogCat(String message) {
        if (message.contains(LINE_SEPARATOR)) {
            for (String line : message.split(LINE_SEPARATOR)) {
                mLogger.log(mLevel, line);
            }
        } else {
            mLogger.log(mLevel, message);
        }
    }

    /**
     * @param tag 头部标志
     * @return 头部
     */
    private String printHeaderLine(String tag) {
        StringBuilder builder = new StringBuilder(TOP_BORDER);
        if (tag.equals("") || TextUtils.isEmpty(tag)) {
            return builder.toString();
        }
        builder.append(HORIZONTAL_DOUBLE_LINE)
                .append(tag)
                .append(LINE_SEPARATOR);
        return builder.toString();
    }

    private String printContentLine(Object object) {
        StringBuilder builder = new StringBuilder(MIDDLE_BORDER);

        // 判断是否是Json数据
        if (object instanceof JSONObject) {
            return builder.toString();
        }
        if (object instanceof JSONArray) {
            return builder.toString();
        }
        if (object instanceof Exception) {
            return builder.toString();
        }

        String message = toString(object);

        builder.append(CONTENT_START_BORDER).append(message);

        return builder.toString();
    }

    private String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }
}
