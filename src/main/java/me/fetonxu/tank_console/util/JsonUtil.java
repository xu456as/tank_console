package me.fetonxu.tank_console.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
    public static JSONObject createJson(String code, String result){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("result", result);
        return json;
    }
}
