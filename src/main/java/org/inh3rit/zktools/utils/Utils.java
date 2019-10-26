package org.inh3rit.zktools.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 12:17 2019/10/26
 */
public class Utils {

    public static String decode(String content) {
        try {
            content = URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }
        return content;
    }
}
