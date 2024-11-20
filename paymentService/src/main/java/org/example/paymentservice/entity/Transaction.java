package org.example.paymentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction extends EntityWithTimestamps {
    String orderId;
    String paymentMethod;
    int amount;
    String status;
}
