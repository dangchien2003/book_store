package org.example.paymentservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditActiveForPaymentMethodRequest {
    @NotNull(message = "DATA_BLANK")
    Boolean active;
}
