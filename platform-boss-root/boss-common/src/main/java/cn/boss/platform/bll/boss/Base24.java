package cn.boss.platform.bll.boss;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by LinShunKang on 4/1/16.
 */
public final class Base24 {
    private static String table = "BCDFGHJKMPQRTVWXY2346789";

    public static String encode(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        }

        int i;
        int position = 0;
        char[] buffer = new char[text.length() << 1];
        while ((i = position) < text.length()) {
            buffer[i << 1] = table.charAt(text.charAt(position) >> 4);
            buffer[(i << 1) + 1] = table.charAt(23 - (text.charAt(position) & 0x0F));
            position++;
        }

        return new String(buffer);
    }

    public static String decode(String text) {
        if (StringUtils.isEmpty(text) || text.length() % 2 != 0) {
            return "";
        }

        int[] position = new int[2];
        char[] buffer = new char[text.length() >> 1];

        for (int i = 0; i < (text.length() >> 1); i++) {
            position[0] = table.indexOf(text.charAt(i << 1));
            position[1] = 23 - table.indexOf(text.charAt((i << 1) + 1));
            if (position[0] < 0 || position[1] < 0) {
                return "";
            }

            buffer[i] = ((char) ((position[0] << 4) | position[1]));
        }
        return new String(buffer);
    }

    public static void main(String[] args) {
        String str = "adsf++--//>>123";
        String strAfterEncode = encode(str);
        String strAfterDecode = decode(strAfterEncode);
        System.out.println(strAfterDecode);
    }
}
