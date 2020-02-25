package com.pillowcase.user.utiils;

import android.text.TextUtils;

import com.pillowcase.user.model.RegularExpression;

/**
 * Author      : PillowCase
 * Create On   : 2020-02-25 13:26
 * Description :
 */
public class UserUtils {

    public static boolean check(String data) {
        boolean result = true;
        if (data.equals("") || TextUtils.isEmpty(data)) {
            result = false;
        }
        if (data.length() > 18 || data.length() < 5) {
            result = false;
        }
        if (!CheckUtils.isMatch(RegularExpression.NODE_CONTENT_REGULAR, data)) {
            result = false;
        }
        return result;
    }
}
