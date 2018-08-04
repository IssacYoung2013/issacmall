package com.imall.util;

import com.google.common.collect.Lists;
import com.imall.pojo.TestPoJo;
import com.imall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author Issac
 *  *   @date    2018-08-03
 * @desc
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
       objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

       //取消默认转换timestamp形式
       objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

       // 忽略空Bean转json的错误
       objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

       // 所有的日期格式都统一样式
       objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

       // 忽略 在json字符串存在，但是在java对象中不存在对应属性的情况，防止错误
       objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    }

    public static <T> String obj2String(T obj){
        if(obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if(obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz) {
        if(StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (Exception e) {
            log.warn("Parse String to object error",e);
            
            return null;
        }
    }

    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if(StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }

        try {
            return (T)(typeReference.getType().equals(String.class)?str:objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to object error",e);

            return null;
        }
    }

    public static <T> T stringToObj(String str, Class<?> collectionClass,Class<?> ... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);

        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to object error",e);

            return null;
        }
    }



    public static void main(String[] args) {

        TestPoJo testPoJo = new TestPoJo();
        testPoJo.setId(1);
        testPoJo.setName("Issac");
        String testPojoJson = JsonUtil.obj2String(testPoJo);
//        {"name":"Issac","id":1}
        log.info(testPojoJson);

        String json ="{\"name\":\"Issac\",\"color\":\"Issac\",\"id\":1}";
        TestPoJo testPoJo1 = JsonUtil.string2Obj(json,TestPoJo.class);


//        User u1 = new User();
//        u1.setId(2);
//        u1.setUsername("");
//        u1.setCreateTime(new Date());
//        u1.setEmail("issacyoung@msn.cn");
//
//        String user1Json = JsonUtil.obj2String(u1);
//
//        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);
//        log.info(user1JsonPretty);
        
//        System.out.println(user1Json);
//
//        System.out.println(user1JsonPretty);
//
//        User user = JsonUtil.string2Obj(user1Json,User.class);
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(u1);
//        userList.add(user);
//
//
//        String userListStr = JsonUtil.obj2StringPretty(userList);
//
//        System.out.println(userListStr);
//
//        log.info("===========");
//
//        List<User> userListObj1 = JsonUtil.stringToObj(userListStr,new TypeReference<List<User>>(){});
//
//        List<User> userListObj2 = JsonUtil.stringToObj(userListStr,List.class,User.class);


        System.out.println("end");
    }

}
