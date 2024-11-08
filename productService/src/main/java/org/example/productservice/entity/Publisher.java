package org.example.productservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Publisher extends EntityWithTimestamps {
    Integer id;
    String name;
    String website;
}
