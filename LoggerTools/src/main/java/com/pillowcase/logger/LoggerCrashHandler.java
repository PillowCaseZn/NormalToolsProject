package com.pillowcase.logger;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-05 14:00
 * Description ： 用来处理在程序中未被捕获的异常。（如果程序中已经自己设置了try{}catch，则不会执行这个方法）。
 */
public class LoggerCrashHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //处理异常信息
    }
}
