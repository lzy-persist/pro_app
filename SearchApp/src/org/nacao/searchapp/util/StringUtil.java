package org.nacao.searchapp.util;

public class StringUtil {

    public static String convertNull(String str) {
        boolean flag = false;
        if (str == null || "null".equals(str)) {
            return "";
        } else {
            return str.trim();
        }
    }
    
    public static boolean isEmpty(String str) {

        boolean flag = false;
        if (str == null || "".equals(str) || "null".equals(str)) {

            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
