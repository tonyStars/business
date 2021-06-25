package com.club.business.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Tom
 * @Description: 基于fastjson封装的json转换工具类
 * @date 2019-12-05
 */
public class JsonUtils {


    /**
     * 功能描述：把JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     *
     * @param object java对象
     * @return JSON数据
     */
    public static String getBeanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 功能描述：把JSON数组字符串转换成指定的java对象集合
     *
     * @param jsonData JSON数组字符串
     * @param clazz    指定的java对象
     * @return List<T>
     */
    public static <T> List<T> getJsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数组字符串转换成较为复杂的List<Map<String, Object>>
     *
     * @param jsonData JSON数组字符串
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }


    public static void main(String[] args) {
        String str = "{\"isSuc\":true,\"msg\":\"操作成功\",\"data\":\"ok\"}";
        Object obj = getJsonToBean(str, Map.class);
        Map map = (Map) obj;
        System.out.println(map.size());
        Iterator<Map.Entry> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
      String jstr =   getBeanToJson(map);
    }

}