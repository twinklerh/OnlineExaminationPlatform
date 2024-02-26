package com.oep.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WriteValue {
    public static <T> String writeValueAsString(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsString(object);
        } catch(JsonProcessingException e){
            System.out.println("格式转化异常");
        }
        return "error";
    }
    public static ObjectNode writeValueAsJSON(String s){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return (ObjectNode) objectMapper.readTree(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
