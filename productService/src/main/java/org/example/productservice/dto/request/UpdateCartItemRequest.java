package org.example.productservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {
    @NotNull(message = "DATA_BLANK")
    Long bookId;

    @Min(value = 1, message = "QUANTITY_TOO_SMALL")
    @Max(value = 30, message = "QUANTITY_TOO_LARGE")
    int quantity;

    @NotBlank(message = "DATA_BLANK")
    String changeType;
}
