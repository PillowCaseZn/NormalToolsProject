package com.pillowcase.normal.tools.logger;


import android.util.Log;

import com.pillowcase.normal.tools.logger.utils.Utils;

import java.util.List;


/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 16:21
 * Description :
 */
public class LoggerBase {
    private boolean isDebug;
    private String Logger_Tag;
    private int log_length = 300;
    private String top_div = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private String div = "│";
    private String center_div = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private String bottom_div = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────";

    public LoggerBase(boolean isDebug, String logger_Tag) {
        this.isDebug = isDebug;
        Logger_Tag = logger_Tag;
    }

    public void log(String s, Object o) {
        if (isDebug) {
            Log.d(Logger_Tag, top_div);
            Log.d(Logger_Tag, div + "\u3000" + "Method:" + s);
            if (o != null && Utils.toString(o) != null && !Utils.toString(o).equals("")) {
                Log.d(Logger_Tag, center_div);
                List<String> list = Utils.formatObject(Utils.toString(o), log_length);
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
                List<String> list = Utils.formatObject(s1, log_length);
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
                List<String> list = Utils.formatObject(Utils.getStackTraceString(throwable), log_length);
                for (int i = 0; i < list.size(); i++) {
                    Log.e(Logger_Tag, div + "\u3000" + list.get(i));
                }
            }
            Log.e(Logger_Tag, bottom_div);
        }
    }
}
