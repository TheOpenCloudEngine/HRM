package org.opencloudengine.garuda.backend.clientjob;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencloudengine.garuda.model.request.FieldType;
import org.opencloudengine.garuda.model.request.HiveRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by uengine on 2016. 9. 6..
 */
public class JacksonTest {
    public static void main(String args[]) throws Exception{
        HiveRequest request = new HiveRequest();
        ObjectMapper objectMapper = new ObjectMapper();

        //objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        System.out.println(s);

        Method methods[] = HiveRequest.class.getDeclaredMethods();

        for (Method method : methods) {
            if(method.getName().startsWith("get")){
                String name = method.getName();
                name = name.replaceFirst("get","");
                name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                System.out.println(name);

                if(method.isAnnotationPresent(FieldType.class)){
                    FieldType fieldType = method.getAnnotation(FieldType.class);
                    System.out.println(fieldType.description());
                }
            }
        }
    }
}
