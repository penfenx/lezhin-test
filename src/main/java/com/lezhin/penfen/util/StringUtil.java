package com.lezhin.penfen.util;

public class StringUtil {

    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Filters out special characters except for Korean, English, and numbers.
     *
     * @param str input string
     * @return filtered string
     */
    public static String removeSpecialCharacters(String str) {
        return str.replaceAll("[^가-힣a-zA-Z0-9\\s]", "");
    }
}