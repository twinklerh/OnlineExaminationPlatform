package com.oep.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteValueAsString {
    public static <T> String writeValueAsString(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(object);
        } catch(JsonProcessingException e){
            System.out.println("格式转化异常");
        }
        return "error";
    }
}
