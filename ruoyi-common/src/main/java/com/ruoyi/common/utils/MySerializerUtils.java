package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MySerializerUtils extends JsonSerializer<Long> {
    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String statusStr = String.valueOf(id);
         jsonGenerator.writeString(statusStr);
     }
 }
