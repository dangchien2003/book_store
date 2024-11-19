package org.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {
    @Size(min = 1, message = "PRODUCT_QUANTITY_IS_TOO_SMALL")
    @Size(max = 10, message = "PRODUCT_QUANTITY_IS_TOO_LARGE")
    Set<ItemOrder> items;

    @NotBlank(message = "DATA_BLANK")
    String paymentMethod;

    @NotBlank(message = "DATA_BLANK")
    String name;

    @NotNull(message = "DATA_BLANK")
    Integer communeAddressCode;

    @NotBlank(message = "DATA_BLANK")
    String phoneNumber;

    @NotBlank(message = "DATA_BLANK")
    String detailAddress;
}
