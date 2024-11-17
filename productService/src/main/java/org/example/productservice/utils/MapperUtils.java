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
            result.append(capitalize(word));
        }

        return result.toString();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static <T> T mappingOneElement(Class<T> clazz, Map<String, Object> data) throws Exception {
        return mappingOneElement(clazz, data, genDictionary(data));

    }

    public static <T> T mappingOneElement(Class<T> clazz, Map<String, Object> data, Map<String, String> dictionary)
            throws Exception {

        T instance = clazz.getDeclaredConstructor().newInstance();
        Class<?> superclass = clazz.getSuperclass();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

//            if (Objects.isNull(fieldValue)) {
//                continue;
//            }

            try {
                Field field = findField(clazz, superclass, dictionary.get(fieldName));
                if (field != null) {
                    field.setAccessible(true);
                    setFieldValue(instance, field, fieldValue);
//                    String setterName = "set" + capitalize(dictionary.get(fieldName));
//                    Method setterMethod = clazz.getMethod(setterName, field.getType());
//                    setterMethod.invoke(instance, fieldValue);
                }
            } catch (NoSuchFieldException e) {
                log.warn("No such field: " + fieldName);
            } catch (NullPointerException e) {
                log.warn("Field: " + fieldName + " is null");
            } catch (Exception e) {
                log.error("Mapper utils error: ", e);
            }
        }

        return instance;
    }

    private static Field findField(Class<?> clazz, Class<?> superclass, String fieldName) throws NoSuchFieldException {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (superclass != null) {
                field = superclass.getDeclaredField(fieldName);
            }
        }
        return field;
    }

    private static <T> void setFieldValue(T instance, Field field, Object fieldValue) throws IllegalAccessException {
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

        return result;
    }
}
