package org.example.orderservice.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.util.TimeUtils;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityWithTimestamps {
    Long createdAt;
    Long modifiedAt;

    public void onCreate() {
        createdAt = TimeUtils.getTimeStamp();
    }

    public void onUpdate() {
        modifiedAt = TimeUtils.getTimeStamp();
    }
}
