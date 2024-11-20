package org.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePaymentMethodRequest {
    @NotBlank(message = "DATA_BLANK")
    String id;

    @NotBlank(message = "DATA_BLANK")
    String name;

    String description;
}
