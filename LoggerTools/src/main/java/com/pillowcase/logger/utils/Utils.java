package com.pillowcase.logger.utils;

import com.pillowcase.logger.LoggerUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-26 11:06
 * Description :
 */
public class Utils {
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static List<String> formatObject(String data, int MAX_LOG_LENGTH) {
        List<String> dataList = new ArrayList<>();
        if (data.contains("\n")) {
            String[] split = data.split("\n");
            for (String s1 : split) {
                if (s1.length() > MAX_LOG_LENGTH) {
                    dataList.add(s1.substring(0, MAX_LOG_LENGTH));
                    dataList.add(s1.substring(MAX_LOG_LENGTH + 1));
                } else {
                    dataList.add(s1);
                }
            }
        } else {
            if (data.length() > MAX_LOG_LENGTH) {
                dataList.add(data.substring(0, MAX_LOG_LENGTH));
                dataList.add(data.substring(MAX_LOG_LENGTH + 1));
            } else {
                dataList.add(data);
            }
        }
        return dataList;
    }

    public static int getStackOffset(StackTraceElement[] trace, int MIN_STACK_OFFSET) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerUtils.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    public static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static String toString(Object object) {
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
