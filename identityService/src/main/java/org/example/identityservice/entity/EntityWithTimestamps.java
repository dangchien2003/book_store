package org.example.identityservice.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.utils.TimeUtils;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PROTECTED)
public class EntityWithTimestamps {
    @NotNull
    Long createdAt;
    Long modifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = TimeUtils.getTimeStamp();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = TimeUtils.getTimeStamp();
    }
}
