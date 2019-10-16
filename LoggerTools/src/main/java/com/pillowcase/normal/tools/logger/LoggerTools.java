package com.pillowcase.normal.tools.logger;


import com.pillowcase.normal.tools.logger.impl.FormatStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 11:55
 * Description :
 */
public class LoggerTools {
    private String div = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";

    public LoggerTools() {
    }

    public void init(String Tag, final boolean debug) {
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)//是否显示线程信息。默认的真实
                .methodCount(0)         //显示多少个方法行。默认值2
                .methodOffset(7)        // 隐藏内部方法调用，直到偏移量。默认的5
                .tag(Tag)   // 全局标签。默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
    }

    /**
     * 普通日志输出
     *
     * @param method 方法名
     * @param object 内容
     */
    public void log(String method, Object object) {
        if (object instanceof JSONObject || object instanceof JSONArray) {
            Logger.json(String.valueOf(object));
        } else {
            Logger.d("Method:%s\nMessage:\n%s\n%s", method, div, Utils.toString(object));
        }
    }

    /**
     * 警告日志输出
     *
     * @param method  方法名
     * @param message 内容
     */
    public void warn(String method, String message) {
        Logger.w("Method:%s\nMessage:\n%s\n%s", method, div, message);
    }

    /**
     * 异常捕获日志输出
     *
     * @param throwable 捕获异常
     * @param method    发生异常的方法名
     */
    public void error(Throwable throwable, String method) {
        Logger.e("Method:%s\nMessage:\n%s\n%s", method, div, throwable);
    }

    public void json(String json) {
        Logger.json(json);
    }

    public void xml(String xml) {
        Logger.xml(xml);
    }
}
