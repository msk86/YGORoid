package android.ygo.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharSet {
    public static final String[] CODE_NAME = {"GBK", "UTF-8", "UTF-16"};


    public static String detect(File file) {
        for (int i = 0; i < CODE_NAME.length; i++) {
            char[] buf = new char[(int) file.length()];
            try {
                BufferedReader reader = new BufferedReader(new UnicodeReader(new FileInputStream(file), CODE_NAME[i]));
                reader.read(buf);
                String s = new String(buf);
                if (noReadableWord(s)) {
                    reader.close();
                    return CODE_NAME[i];
                }
                reader.close();
            } catch (Exception e) {
            }
        }
        return "UTF-8";
    }

    private static boolean noReadableWord(String s) {
        //ascii区间、希腊字母区间、汉字区间、汉字符号区间（可能不完善）
        Pattern pattern = Pattern.compile("^([\\u0000-\\u007F\\u00b7\\u0391-\\u03a9\\u03b1-\\u03c9\\u2014\\u221E\\u2606\\u3000-\\u3011\\u4e00-\\u9fa5\\uff01-\\uff20]+)$");
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
