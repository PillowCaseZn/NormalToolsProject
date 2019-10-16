package com.pillowcase.normal.tools.logger.impl;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 11:55
 * Description :
 */
public interface ILoggerOperation {
    /**
     * 普通日志输出
     *
     * @param method 方法名
     * @param object 内容
     */
    void log(String method, Object object);


    /**
     * 警告日志输出
     *
     * @param method  方法名
     * @param message 内容
     */
    void warn(String method, String message);

    /**
     * 异常捕获日志输出
     *
     * @param throwable 捕获异常
     * @param method    发生异常的方法名
     */
    void error(Throwable throwable, String method);
}
