package org.example.productservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends EntityWithTimestamps {
    String userId;
    long bookId;
    int quantity;
}
