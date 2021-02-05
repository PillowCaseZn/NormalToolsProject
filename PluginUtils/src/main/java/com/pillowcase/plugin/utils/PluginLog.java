package com.pillowcase.plugin.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 00:58
 * Description ： 日志输出
 */
public class PluginLog {
    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final char MIDDLE_CORNER = '╟';
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DATA_SEPARATOR = "\t";

    public static final String DOUBLE_DIVIDER = "══════════════════════════════════════════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "──────────────────────────────────────────────────────────────────────────────────";

    private static final String HEADER_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + LINE_SEPARATOR;
    private static final String BORDER_BORDER = LINE_SEPARATOR + BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER;

    private static Logger mLogger;
    private static Level mLoggerLevel = Level.INFO;

    private enum PrintType {
        Default,
        List,
        JsonObject,
        JsonArray,
        String,
        Error
    }

    static {
        mLogger = Logger.getLogger("PluginLog");
    }

    public static void log(String message) {
        log(null, message);
    }

    public static void error(Exception e) {
        error(null, e);
    }

    public static void log(String tag, Object object) {
        mLoggerLevel = Level.INFO;
        printInLogCat(tag, object);
    }

    public static void error(String tag, Exception e) {
        mLoggerLevel = Level.WARNING;
        printInLogCat(tag, e);
    }

    private static void printInLogCat(String tag, Object o) {
        try {
            StringBuilder MESSAGE_CONTENT = new StringBuilder(HEADER_BORDER);
            if (tag != null) {
                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append(tag)
                        .append(LINE_SEPARATOR)
                        .append(MIDDLE_CORNER)
                        .append(SINGLE_DIVIDER)
                        .append(LINE_SEPARATOR);
            }

            if (o instanceof List) {
                MESSAGE_CONTENT.append(printLogger(PrintType.List, o));
            } else if (o instanceof JSONArray) {
                MESSAGE_CONTENT.append(printLogger(PrintType.JsonArray, o));
            } else if (o instanceof JSONObject) {
                MESSAGE_CONTENT.append(printLogger(PrintType.JsonObject, o));
            } else if (o instanceof String) {
                MESSAGE_CONTENT.append(printLogger(PrintType.String, o));
            } else if (o instanceof Exception) {
                MESSAGE_CONTENT.append(printLogger(PrintType.Error, o));
            } else {
                MESSAGE_CONTENT.append(printLogger(PrintType.Default, o));
            }
            MESSAGE_CONTENT.append(BORDER_BORDER);
            printInLogCat(MESSAGE_CONTENT.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String printLogger(PrintType type, Object o) {
        StringBuilder MESSAGE_CONTENT = new StringBuilder();
        try {
            switch (type) {
                case List:
                    ArrayList dataList = (ArrayList) o;
                    for (int i = 0; i < dataList.size(); i++) {
                        String message = toString(dataList.get(i));
                        if (message.contains("\n") && message.endsWith("}")) {
                            MESSAGE_CONTENT.append(formatString(message));
                        } else if (message.contains("'") && message.endsWith("}")) {
                            MESSAGE_CONTENT.append(formatString(message));
                        } else {
                            MESSAGE_CONTENT.append(message);
                        }
                        MESSAGE_CONTENT.append(LINE_SEPARATOR);
                    }
                    break;
                case JsonObject:
                    JSONObject jsonObject = (JSONObject) o;
                    MESSAGE_CONTENT.append(formatJsonObject(jsonObject));
                    break;
                case JsonArray:
                    JSONArray jsonArray = (JSONArray) o;
                    MESSAGE_CONTENT.append(formatJsonArray(jsonArray));
                    break;
                case String:
                    String content = (String) o;
                    if (content.startsWith("[") && content.endsWith("]")) {
                        MESSAGE_CONTENT.append(formatJsonArray(new JSONArray(content)));
                    } else if (content.startsWith("{") && content.endsWith("}")) {
                        MESSAGE_CONTENT.append(formatJsonObject(new JSONObject(content)));
                    } else {
                        MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                                .append(content);
                    }
                    break;
                case Error:
                    Exception e = (Exception) o;
                    MESSAGE_CONTENT.append(printThreadInfo(e));
                    break;
                case Default:
                    String message = toString(o);
                    if (message.contains("\n") && message.endsWith("}")) {
                        MESSAGE_CONTENT.append(formatString(message));
                    } else if (message.contains("'") && message.endsWith("}")) {
                        MESSAGE_CONTENT.append(formatString(message));
                    } else {
                        MESSAGE_CONTENT.append(message);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_CONTENT.toString();
    }

    /**
     * @return 输出线程信息
     */
    private static String printThreadInfo(Exception exception) {
        StringBuilder MESSAGE_CONTENT = new StringBuilder();
        try {
            MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                    .append(exception)
                    .append(LINE_SEPARATOR);

            StackTraceElement[] trace = exception.getStackTrace();
            for (StackTraceElement element : trace) {
                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append(DATA_SEPARATOR)
                        .append(element)
                        .append(LINE_SEPARATOR);
            }

            Throwable[] suppressed = exception.getSuppressed();
            if (suppressed.length > 0) {
                MESSAGE_CONTENT.append(MIDDLE_CORNER)
                        .append(SINGLE_DIVIDER)
                        .append(LINE_SEPARATOR);

                for (Throwable throwable : suppressed) {
                    MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                            .append(DATA_SEPARATOR)
                            .append(throwable)
                            .append(LINE_SEPARATOR);
                }
            }

            Throwable cause = exception.getCause();
            if (cause != null) {
                MESSAGE_CONTENT.append(MIDDLE_CORNER)
                        .append(SINGLE_DIVIDER)
                        .append(LINE_SEPARATOR);

                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append("Caused by: ")
                        .append(cause)
                        .append(LINE_SEPARATOR);

                trace = cause.getStackTrace();
                for (StackTraceElement element : trace) {
                    MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                            .append(DATA_SEPARATOR)
                            .append(element)
                            .append(LINE_SEPARATOR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_CONTENT.toString();
    }

    private static void printInLogCat(String message) {
        try {
            if (message.contains(LINE_SEPARATOR)) {
                String[] dataLine = message.split(LINE_SEPARATOR);
                for (String line : dataLine) {
                    mLogger.log(mLoggerLevel, line);
                }
            } else {
                mLogger.log(mLoggerLevel, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatJsonArray(JSONArray jsonArray) {
        StringBuilder MESSAGE_CONTENT = new StringBuilder();
        try {

            if (jsonArray.length() == 0) {
                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append("[]")
                        .append(LINE_SEPARATOR);
            } else {
                MESSAGE_CONTENT
                        .append(HORIZONTAL_DOUBLE_LINE)
                        .append("[")
                        .append(LINE_SEPARATOR);
                for (int i = 0; i < jsonArray.length(); i++) {
                    MESSAGE_CONTENT.append(formatJsonObject(jsonArray.getJSONObject(i)));
                }
                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append("]")
                        .append(LINE_SEPARATOR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_CONTENT.toString();
    }

    private static String formatJsonObject(JSONObject jsonObject) {
        StringBuilder MESSAGE_CONTENT = new StringBuilder();
        try {
            MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                    .append(DATA_SEPARATOR)
                    .append("{")
                    .append(LINE_SEPARATOR);

            Iterator<String> it = jsonObject.keys();
            while (it.hasNext()) {
                String key = it.next();
                MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                        .append(DATA_SEPARATOR)
                        .append(DATA_SEPARATOR)
                        .append('\"')
                        .append(key)
                        .append('\"')
                        .append(":")
                        .append('\"')
                        .append(jsonObject.getString(key))
                        .append('\"')
                        .append(LINE_SEPARATOR);
            }
            MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                    .append(DATA_SEPARATOR)
                    .append("}")
                    .append(LINE_SEPARATOR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_CONTENT.toString();
    }

    private static String formatString(String message) {
        StringBuilder MESSAGE_CONTENT = new StringBuilder();
        try {
            MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                    .append(message.substring(0, message.indexOf("{") + 1))
                    .append(LINE_SEPARATOR);

            String content = message.substring(message.indexOf("{") + 1, message.length() - 1);

            String[] split = null;
            if (content.contains("\n")) {
                split = content.split("\n");
            } else if (content.contains(",")) {
                split = content.split(",");
            }
            if (split != null) {
                for (String line : split) {
                    line = line.trim();
                    if (line.equals("")) {
                        continue;
                    }
                    line = DATA_SEPARATOR + line;

                    if (line.length() > DOUBLE_DIVIDER.length()) {
                        StringBuilder lineBuilder = new StringBuilder();
                        while (line.length() > DOUBLE_DIVIDER.length()) {
                            lineBuilder.append(line.substring(0, DOUBLE_DIVIDER.length()))
                                    .append(LINE_SEPARATOR);
                            line = DATA_SEPARATOR + DATA_SEPARATOR + line.substring(DOUBLE_DIVIDER.length());
                        }
                        if (line.length() > 0) {
                            lineBuilder.append(HORIZONTAL_DOUBLE_LINE)
                                    .append(line)
                                    .append(LINE_SEPARATOR);
                        }
                        MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                                .append(lineBuilder)
                                .append(LINE_SEPARATOR);
                    } else {
                        MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                                .append(line)
                                .append(LINE_SEPARATOR);
                    }
                }
            }
            MESSAGE_CONTENT.append(HORIZONTAL_DOUBLE_LINE)
                    .append(message.substring(message.length() - 1))
                    .append(LINE_SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MESSAGE_CONTENT.toString();
    }


    private static String toString(Object object) {
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
