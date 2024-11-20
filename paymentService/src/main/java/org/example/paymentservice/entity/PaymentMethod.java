package org.example.paymentservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod extends EntityWithTimestamps {
    String id;
    String name;
    String description;
    byte active;
}
