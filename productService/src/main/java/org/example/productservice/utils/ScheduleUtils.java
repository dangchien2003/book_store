package org.example.productservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.productservice.configuration.TaskSchedulerConfig;

@Slf4j
public class ScheduleUtils {
    public static void addScheduleRemoveImage(String url) {
        if (!TaskSchedulerConfig.REMOVE_IMAGE.offer(url)) {
            log.error("can not add remove image: " + url);
        }
    }

    public static void addScheduleCacheProduct(Long id) {
        if (!TaskSchedulerConfig.PRODUCT_UPDATE.offer(id)) {
            log.error("can not add cache product: " + id);
        }
    }
}
