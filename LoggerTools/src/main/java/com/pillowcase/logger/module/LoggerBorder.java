package com.pillowcase.logger.module;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-25 15:42
 * Description :
 */
public class LoggerBorder {
    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char MIDDLE_CORNER = '╟';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final String DOUBLE_DIVIDER = "═══════════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "───────────────────────────────────────────────────";
    public static final String DATA_SEPARATOR = "\u2000";

    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

}
