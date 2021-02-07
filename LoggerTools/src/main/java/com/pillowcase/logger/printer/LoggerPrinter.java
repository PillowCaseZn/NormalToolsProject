package com.pillowcase.logger.printer;

import com.pillowcase.logger.module.PillowLoggerBorder;

import java.util.Arrays;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:07
 * Description ：
 */
public abstract class LoggerPrinter {
    protected static final String DOUBLE_DIVIDER = PillowLoggerBorder.DOUBLE_DIVIDER;

    protected static final String LINE_SEPARATOR = PillowLoggerBorder.LINE_SEPARATOR;
    protected static final String DATA_SEPARATOR = PillowLoggerBorder.DATA_SEPARATOR;

    protected static final String MIDDLE_BORDER = PillowLoggerBorder.MIDDLE_BORDER;
    protected static final String CONTENT_START_BORDER = PillowLoggerBorder.CONTENT_START_BORDER;

    protected static String ArraysToString(Object object) {
        if (object instanceof String[]) {
            return Arrays.toString((String[]) object);
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
        return null;
    }

    protected static String ObjectToString(Object object) {
        if (object instanceof String) {
            return String.valueOf(object);
        }
        if (object instanceof Boolean) {
            return Boolean.toString((Boolean) object);
        }
        if (object instanceof Byte) {
            return Byte.toString((Byte) object);
        }
        if (object instanceof Short) {
            return Short.toString((Short) object);
        }
        if (object instanceof Integer) {
            return Integer.toString((Integer) object);
        }
        if (object instanceof Long) {
            return Long.toString((Long) object);
        }
        if (object instanceof Float) {
            return Float.toString((Float) object);
        }
        if (object instanceof Double) {
            return Double.toString((Double) object);
        }
        if (object instanceof Character) {
            return Character.toString((Character) object);
        }
        return null;
    }

    public abstract StringBuilder printData(Object object);
}
