package com.pillowcase.logger.module;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:09
 * Description ：
 */
public class PillowLoggerBorder {
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char MIDDLE_CORNER = '╟';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';

    public static final String DOUBLE_DIVIDER = "══════════════════════════════════════════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "──────────────────────────────────────────────────────────────────────────────────";

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DATA_SEPARATOR = "\t";

    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + LINE_SEPARATOR;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + LINE_SEPARATOR;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER;

    public static final String CONTENT_START_BORDER = HORIZONTAL_DOUBLE_LINE + DATA_SEPARATOR;
}
