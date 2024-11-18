package org.example.orderservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends EntityWithTimestamps {
    String id;
    String purchaseBy;
    int communeAddressCode;
    String paymentId;
    String statusCode;
    String recipientName;
    String phoneNumber;
    String detailAddress;
    String trackingCode;
}
