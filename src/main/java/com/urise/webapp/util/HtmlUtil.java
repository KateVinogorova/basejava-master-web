package com.urise.webapp.util;

public class HtmlUtil {

    public static boolean isEmpty(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
