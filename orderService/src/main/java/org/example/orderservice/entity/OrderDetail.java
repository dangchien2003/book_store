package org.example.orderservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail extends EntityWithTimestamps {
    String orderId;
    long bookId;
    int quantity;
    int price;
}
