package com.oep.backend.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;

public class WriteValue {
    public static <T> String writeValueAsString(T object) {
        try {
            return JSON.toJSONString(object);
        } catch (JSONException e) {
            System.out.println("格式转化异常");
        }
        return "error";
    }
}
