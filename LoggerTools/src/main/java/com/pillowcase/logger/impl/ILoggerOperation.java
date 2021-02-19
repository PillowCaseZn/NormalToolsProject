package com.pillowcase.logger.impl;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-12 11:55
 * Update On   : 2021-02-19 10:29
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
     * 异常捕获日志输出
     *
     * @param method    发生异常的方法名
     * @param exception 异常捕获
     */
    void error(String method, Exception exception);
}
