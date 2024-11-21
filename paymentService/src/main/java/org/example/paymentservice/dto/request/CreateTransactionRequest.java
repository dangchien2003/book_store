package org.example.paymentservice.dto.request;

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
    String method;
    @NotBlank(message = "DATA_BLANK")
    String orderId;
    @Min(value = 0, message = "INVALID_AMOUNT")
    int amount;
    @NotBlank(message = "DATA_BLANK")
    String ipAddress;
}
