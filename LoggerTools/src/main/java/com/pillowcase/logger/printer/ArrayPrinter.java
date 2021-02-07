package com.pillowcase.logger.printer;

import com.pillowcase.logger.format.StringFormat;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:08
 * Description ： 数组
 */
public class ArrayPrinter extends LoggerPrinter {

    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder(CONTENT_START_BORDER);

        if (ArraysToString(object) != null) {
            return builder.append(ArraysToString(object)).append(LINE_SEPARATOR);
        } else if (object instanceof Object[]) {
            Object[] objects = (Object[]) object;
            builder.append("Arrays length : ")
                    .append(objects.length)
                    .append(LINE_SEPARATOR)
                    .append(MIDDLE_BORDER)
                    .append(CONTENT_START_BORDER)
                    .append("[")
                    .append(LINE_SEPARATOR);
            for (int i = 0; i < objects.length; i++) {
                Object o = objects[i];
                builder.append(StringFormat.format(o.toString()) );
                if (i != objects.length - 1) {
                    builder.deleteCharAt(builder.lastIndexOf(LINE_SEPARATOR))
                            .append(",")
                            .append(LINE_SEPARATOR);
                }
            }
            return builder.append(CONTENT_START_BORDER).append("]").append(LINE_SEPARATOR);
        }

        return builder.append(LINE_SEPARATOR);
    }
}
