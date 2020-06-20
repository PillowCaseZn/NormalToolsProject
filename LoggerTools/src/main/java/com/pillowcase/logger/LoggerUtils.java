package com.pillowcase.logger;

import com.pillowcase.logger.module.Border;
import com.pillowcase.logger.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-25 15:39
 * Description :
 */
public class LoggerUtils {
    private Logger mLogger;
    private final int MIN_STACK_OFFSET = 5;
    private int MAX_LOG_LENGTH = 300;

    /**
     * 是否显示线程信息
     */
    private boolean showThreadInfo = false;
    /**
     * 显示多少个方法行
     */
    private int methodCount = 10;
    /**
     * 隐藏内部方法调用，直到偏移量
     */
    private int methodOffset = 5;

    private String LOG_HEADER_SEPARATOR;

    private String TOP_BORDER;
    private String MIDDLE_BORDER;
    private String BOTTOM_BORDER;

    public LoggerUtils(boolean isDebug, final String logger_Tag) {
        mLogger = Logger.getLogger(logger_Tag);

        StringBuilder lineBuilder = new StringBuilder(" ");
        for (int i = 1; i < logger_Tag.length(); i++) {
            lineBuilder.append(" ");
        }
        LOG_HEADER_SEPARATOR = lineBuilder.toString();

        TOP_BORDER = Border.TOP_BORDER + Border.LINE_SEPARATOR;
        MIDDLE_BORDER = LOG_HEADER_SEPARATOR + Border.MIDDLE_BORDER + Border.LINE_SEPARATOR;
        BOTTOM_BORDER = LOG_HEADER_SEPARATOR + Border.BOTTOM_BORDER;

        MAX_LOG_LENGTH = TOP_BORDER.length() - LOG_HEADER_SEPARATOR.length();

        if (isDebug) {
            mLogger.setLevel(Level.ALL);
        } else {
            mLogger.setLevel(Level.WARNING);
        }
    }

    public void log(String method, Object object) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(printHeader(method));

            if (showThreadInfo) {
                builder.append(printThreadInfo());
            }
            builder.append(MIDDLE_BORDER);

            String data = Utils.toString(object);
            if (object == null || data.equals("") || data.equals("null")) {
                builder.replace(builder.length() - MIDDLE_BORDER.length(), builder.length(), BOTTOM_BORDER);
            } else {
                List<String> output = new ArrayList<>();

                if (object instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) object;
                    output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                } else if (object instanceof JSONArray) {
                    JSONArray jsonObject = (JSONArray) object;
                    output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                } else if (data.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(data);
                    output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                } else if (data.startsWith("[")) {
                    if (object instanceof List) {
                        output.add("[");
                        List<Object> list = (List<Object>) object;
                        for (Object o : list) {
                            output.add(Border.DATA_SEPARATOR + Utils.toString(o));
                        }
                        output.add("]");
                    } else {
                        JSONArray jsonObject = new JSONArray(data);
                        output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                    }
                } else {
                    output.addAll(Utils.formatObject(data, MAX_LOG_LENGTH));
                }
                for (String i : output) {
                    builder.append(printData(i));
                }
                builder.append(BOTTOM_BORDER);
            }
            mLogger.info(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void warn(String method, String message) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(printHeader(method));

            if (showThreadInfo) {
                builder.append(printThreadInfo());
            }
            builder.append(MIDDLE_BORDER);

            if (message.equals("")) {
                builder.replace(builder.length() - MIDDLE_BORDER.length(), builder.length(), BOTTOM_BORDER);
            } else {
                List<String> output = new ArrayList<>();

                if (message.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(message);
                    output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                } else {
                    if (message.startsWith("[\"") && message.endsWith("\"]")) {
                        JSONArray jsonObject = new JSONArray(message);
                        output.addAll(Utils.formatObject(jsonObject.toString(4), MAX_LOG_LENGTH));
                    } else {
                        output.addAll(Utils.formatObject(message, MAX_LOG_LENGTH));
                    }
                }
                for (String i : output) {
                    builder.append(printData(i));
                }
                builder.append(BOTTOM_BORDER);
            }
            mLogger.info(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(Throwable throwable, String method) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(printHeader(method));

            if (showThreadInfo) {
                builder.append(printThreadInfo());
            }
            builder.append(MIDDLE_BORDER);

            if (throwable == null) {
                builder.replace(builder.length() - MIDDLE_BORDER.length(), builder.length(), BOTTOM_BORDER);
            } else {
                if (Objects.equals(throwable.getLocalizedMessage(), "")
                        || Objects.equals(throwable.getMessage(), "")) {
                    builder.replace(builder.length() - MIDDLE_BORDER.length(), builder.length(), BOTTOM_BORDER);
                } else {
                    List<String> output = Utils.formatObject(Utils.getStackTraceString(throwable), MAX_LOG_LENGTH);
                    for (String i : output) {
                        builder.append(printData(i));
                    }
                    builder.append(BOTTOM_BORDER);
                }
            }
            mLogger.warning(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
    }

    private String printHeader(String method) {
        return TOP_BORDER
                + printData("Thread : " + Thread.currentThread().getName())
                + printData("Method : " + method);
    }

    private String printThreadInfo() {
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = Utils.getStackOffset(trace, MIN_STACK_OFFSET) + methodOffset;

        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            builder.append(LOG_HEADER_SEPARATOR)
                    .append(Border.HORIZONTAL_DOUBLE_LINE)
                    .append(Border.DATA_SEPARATOR)
                    .append(Utils.getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")")
                    .append(Border.LINE_SEPARATOR);
        }
        return builder.toString();
    }

    private String printData(String data) {
        StringBuilder builder = new StringBuilder();
        data = data.trim();
        if (data.contains("\n")) {
            String[] list = data.split("\n");
            for (int i = 0; i < list.length; i++) {
                builder.append(LOG_HEADER_SEPARATOR)
                        .append(Border.HORIZONTAL_DOUBLE_LINE)
                        .append(Border.DATA_SEPARATOR)
                        .append(list[i])
                        .append(Border.LINE_SEPARATOR);
            }
        } else {
            builder.append(LOG_HEADER_SEPARATOR)
                    .append(Border.HORIZONTAL_DOUBLE_LINE)
                    .append(Border.DATA_SEPARATOR)
                    .append(data)
                    .append(Border.LINE_SEPARATOR);
        }
        return builder.toString();
    }
}
