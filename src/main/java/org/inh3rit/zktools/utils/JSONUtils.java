package org.inh3rit.zktools.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 10:54 2019/10/26
 */
public class JSONUtils {

    public static String jsonFormat(String string) {
        String jsonString = string;
        try {
            JSONObject object = JSONObject.parseObject(jsonString);
            if (null == object)
                return string;
            jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            return string;
        }
        return jsonString;
    }
}
