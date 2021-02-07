package com.pillowcase.logger.printer;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 15:23
 * Description ： 异常 Exception
 */
public class ExceptionPrinter extends LoggerPrinter {
    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder(CONTENT_START_BORDER);
        Exception exception = (Exception) object;

        builder.append("Exception : ")
                .append(exception)
                .append(LINE_SEPARATOR)
                .append(MIDDLE_BORDER);

        StackTraceElement[] trace = exception.getStackTrace();
        for (StackTraceElement element : trace) {
            builder.append(CONTENT_START_BORDER)
                    .append(DATA_SEPARATOR)
                    .append("at ")
                    .append(element)
                    .append(LINE_SEPARATOR);
        }

        Throwable[] suppressed = exception.getSuppressed();
        if (suppressed.length > 0) {
            builder.append(MIDDLE_BORDER);

            for (Throwable throwable : suppressed) {
                builder.append(CONTENT_START_BORDER)
                        .append(DATA_SEPARATOR)
                        .append(throwable)
                        .append(LINE_SEPARATOR);
            }
        }

        Throwable cause = exception.getCause();
        if (cause != null) {
            builder.append(MIDDLE_BORDER)
                    .append(CONTENT_START_BORDER)
                    .append("Caused by: ")
                    .append(cause)
                    .append(LINE_SEPARATOR);

            trace = cause.getStackTrace();
            for (StackTraceElement element : trace) {
                builder.append(CONTENT_START_BORDER)
                        .append(DATA_SEPARATOR)
                        .append(element)
                        .append(LINE_SEPARATOR);
            }
        }

        return builder.append(LINE_SEPARATOR);
    }
}
