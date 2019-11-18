package com.pillowcase.normal.tools.only.sign.utils;


import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 16:21
 * Description :
 */
public class BaseLogger {
    private boolean isDebug;
    private String Logger_Tag;
    private int log_length = 300;
    private String top_div = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private String div = "│";
    private String center_div = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private String bottom_div = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────";

    public BaseLogger(boolean isDebug, String logger_Tag) {
        this.isDebug = isDebug;
        Logger_Tag = logger_Tag;
    }

    public void log(String s, Object o) {
        if (isDebug) {
            Log.d(Logger_Tag, top_div);
            Log.d(Logger_Tag, div + "\u3000" + "Method:" + s);
            if (o != null && toString(o) != null && !toString(o).equals("")) {
                Log.d(Logger_Tag, center_div);
                List<String> list = formatObject(o);
                for (int i = 0; i < list.size(); i++) {
                    Log.d(Logger_Tag, div + "\u3000" + list.get(i));
                }
            }
            Log.d(Logger_Tag, bottom_div);
        }
    }

    public void warn(String s, String s1) {
        if (isDebug) {
            Log.w(Logger_Tag, top_div);
            Log.w(Logger_Tag, div + "\u3000" + "Method:" + s);
            if (s1 != null && !s1.equals("")) {
                Log.w(Logger_Tag, center_div);
                List<String> list = formatObject(s1);
                for (int i = 0; i < list.size(); i++) {
                    Log.w(Logger_Tag, div + "\u3000" + list.get(i));
                }
            }
            Log.w(Logger_Tag, bottom_div);
        }
    }

    public void error(Throwable throwable, String s) {
        if (isDebug) {
            Log.e(Logger_Tag, top_div);
            Log.e(Logger_Tag, div + "\u3000" + "Method:" + s);
            if (s != null && !s.equals("")) {
                Log.e(Logger_Tag, center_div);
                List<String> list = formatObject(getStackTraceString(throwable));
                for (int i = 0; i < list.size(); i++) {
                    Log.e(Logger_Tag, div + "\u3000" + list.get(i));
                }
            }
            Log.e(Logger_Tag, bottom_div);
        }
    }

    private List<String> formatObject(Object o) {
        String data = toString(o);
        List<String> dataList = new ArrayList<>();
        if (data.contains("\n")) {
            String[] split = data.split("\n");
            for (int i = 0; i < split.length; i++) {
                String s1 = split[i];
                if (s1.length() > log_length) {
                    dataList.add(s1.substring(0, log_length));
                    dataList.add(s1.substring(log_length + 1));
                } else {
                    dataList.add(s1);
                }
            }
        } else {
            if (data.length() > log_length) {
                dataList.add(data.substring(0, log_length));
                dataList.add(data.substring(log_length + 1));
            } else {
                dataList.add(data);
            }
        }
        return dataList;
    }

    private String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
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
