package org.example.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    @NotBlank(message = "DATA_BLANK")
    String orderId;
    @NotBlank(message = "DATA_BLANK")
    String method;
    @Min(value = 0, message = "INVALID_AMOUNT")
    int amount;
}
