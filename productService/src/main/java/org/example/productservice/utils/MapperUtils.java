package org.example.productservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
public class MapperUtils {

    private MapperUtils() {
    }

    private static Map<String, String> genDictionary(Map<String, Object> data) {
        Map<String, String> dictionary = new HashMap<>();
        Set<String> keys = data.keySet();

        for (String key : keys) {
            if (!key.contains("_"))
                dictionary.put(key, key);

            String[] splitKey = key.split("_");

            dictionary.put(key, joinWithCapitalized(splitKey));
        }
        return dictionary;
    }

    public static String joinWithCapitalized(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }

        return result.toString();
    }

    public static <T> T mappingOneElement(Class<T> clazz, Map<String, Object> data) throws Exception {
        return mappingOneElement(clazz, data, genDictionary(data));

    }

    public static <T> T mappingOneElement(Class<T> clazz, Map<String, Object> data, Map<String, String> dictionary)
            throws Exception {

        if (data.isEmpty())
            return null;

        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            try {
                Field field = clazz.getDeclaredField(dictionary.get(fieldName));
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                if (fieldType == String.class) {
                    field.set(instance, fieldValue.toString());
                } else if (fieldType == int.class || fieldType == Integer.class) {
                    field.set(instance, Integer.parseInt(fieldValue.toString()));
                } else if (fieldType == double.class || fieldType == Double.class) {
                    field.set(instance, Double.parseDouble(fieldValue.toString()));
                } else if (fieldType == float.class || fieldType == Float.class) {
                    field.set(instance, Float.parseFloat(fieldValue.toString()));
                } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                    field.set(instance, Boolean.parseBoolean(fieldValue.toString()));
                } else if (fieldType == long.class || fieldType == Long.class) {
                    field.set(instance, Long.parseLong(fieldValue.toString()));
                } else {
                    field.set(instance, fieldValue);
                }
            } catch (NoSuchFieldException e) {
                log.warn("No such field: " + fieldName);
            }
        }
        return instance;
    }

    public static <T> List<T> mappingManyElement(Class<T> clazz, List<Map<String, Object>> data)
            throws Exception {

        if (data.isEmpty())
            return new ArrayList<>();

        Map<String, String> dictionary = genDictionary(data.get(0));

        List<T> result = new ArrayList<>();
        for (Map<String, Object> element : data) {
            result.add(mappingOneElement(clazz, element, dictionary));
        }

//        int threadPool = 2;
//
//        if (data.size() > 10) {
//            ExecutorService executorService = Executors.newFixedThreadPool(threadPool);
//
//            int elementOneThread = data.size() / threadPool;
//            for (int i = 0; i < threadPool; i++) {
//
//            }
//
//        } else {
//            for (Map<String, Object> element : data) {
//                result.add(mappingOneElement(clazz, element));
//            }
//        }


        return result;
    }
}
