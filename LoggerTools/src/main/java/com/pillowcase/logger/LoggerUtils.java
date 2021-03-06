package com.pillowcase.logger;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pillowcase.logger.module.LoggerBorder;
import com.pillowcase.logger.printer.ArrayPrinter;
import com.pillowcase.logger.printer.ExceptionPrinter;
import com.pillowcase.logger.printer.JsonPrinter;
import com.pillowcase.logger.printer.ListPrinter;
import com.pillowcase.logger.printer.MapPrinter;
import com.pillowcase.logger.printer.StringPrinter;
import com.pillowcase.logger.utils.LoggerCrashHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-06 00:18
 * Description ： Log 日志输出、捕获，输出到LogCat
 */
public class LoggerUtils {
    private static LoggerUtils instance;
    private Logger mLogger;
    private Level mLevel;

    private LoggerUtils() {
        if (mLogger == null) {
            mLogger = Logger.getLogger("Logger");
        }
    }

    public static LoggerUtils getInstance() {
        if (instance == null) {
            synchronized (LoggerUtils.class) {
                if (instance == null) {
                    instance = new LoggerUtils();
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

    /**
     * 需要在项目的Application调用
     */
    public void crash(Application application) {
        Context context = application.getApplicationContext();
        LoggerCrashHandler crashHandler = new LoggerCrashHandler(context);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
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
            builder.append(LoggerBorder.BOTTOM_BORDER);
        } else {
            if (!tag.equals("") && !TextUtils.isEmpty(tag)) {
                builder.append(LoggerBorder.MIDDLE_BORDER);
            }
            String contentLine = printContentLine(object);

            if (contentLine != null && !contentLine.equals("") && !TextUtils.isEmpty(contentLine) && !contentLine.equals(LoggerBorder.MIDDLE_BORDER)) {
                builder.append(contentLine).append(LoggerBorder.LINE_SEPARATOR);
            }

            builder.append(LoggerBorder.BOTTOM_BORDER);
        }
        printLogCat(builder.toString());
    }

    /**
     * 输出日志到LogCat
     *
     * @param message 日志信息
     */
    private void printLogCat(String message) {
        if (message.contains(LoggerBorder.LINE_SEPARATOR)) {
            for (String line : message.split(LoggerBorder.LINE_SEPARATOR)) {
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
        StringBuilder builder = new StringBuilder(LoggerBorder.TOP_BORDER);
        if (tag.equals("") || TextUtils.isEmpty(tag)) {
            return builder.toString();
        }
        builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append(tag)
                .append(LoggerBorder.LINE_SEPARATOR);
        return builder.toString();
    }

    /**
     * @param object 数据内容
     * @return 内容部分
     */
    private String printContentLine(Object object) {
        StringBuilder builder = new StringBuilder();

        if (object instanceof String) {
            // String
            StringBuilder lineBuilder = new StringPrinter().printData(object);
            if (!lineBuilder.toString().equals("") && !TextUtils.isEmpty(lineBuilder.toString())) {
                builder.append(lineBuilder);
                return builder.toString();
            } else {
                return null;
            }
        }
        if (object instanceof JSONObject || object instanceof JSONArray) {
            StringBuilder jsonBuilder = new JsonPrinter().printData(object);
            if (!jsonBuilder.toString().equals("") && !TextUtils.isEmpty(jsonBuilder.toString())) {
                builder.append(jsonBuilder);
                return builder.toString();
            } else {
                return null;
            }
        }
        if (object instanceof List) {
            builder.append(new ListPrinter().printData(object));
            return builder.toString();
        }
        if (object instanceof Exception) {
            builder.append(new ExceptionPrinter().printData(object));
            return builder.toString();
        }
        if (object.getClass().isArray()) {
            // 数组 String[]、 int[]、 double[]等等
            builder.append(new ArrayPrinter().printData(object));
            return builder.toString();
        }
        if (object instanceof Map) {
            builder.append(new MapPrinter().printData(object));
            return builder.toString();
        }
        // 对于其他不属于上述类型的，转换成字符串输出
        builder.append(object);
        return builder.toString();
    }
}
