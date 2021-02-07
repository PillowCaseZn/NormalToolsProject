package com.pillowcase.logger.printer;

import com.pillowcase.logger.format.StringFormat;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-07 14:08
 * Description ： List
 */
public class ListPrinter extends LoggerPrinter {

    @Override
    public StringBuilder printData(Object object) {
        StringBuilder builder = new StringBuilder(CONTENT_START_BORDER);
        List list = (List) object;

        builder.append("List length : ")
                .append(list.size())
                .append(LINE_SEPARATOR)
                .append(MIDDLE_BORDER)
                .append(CONTENT_START_BORDER)
                .append("[")
                .append(LINE_SEPARATOR);

        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            builder.append(StringFormat.format(o.toString()));
            if (i != list.size() - 1) {
                builder.deleteCharAt(builder.lastIndexOf(LINE_SEPARATOR))
                        .append(",")
                        .append(LINE_SEPARATOR);
            }
        }

        return builder.append(CONTENT_START_BORDER).append("]").append(LINE_SEPARATOR);
    }
}
