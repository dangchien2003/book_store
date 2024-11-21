package org.example.productservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookMinusQuantityRequest {
    @NotNull(message = "DATA_BLANK")
    Long bookId;
    
    @NotNull(message = "DATA_BLANK")
    @Min(value = 1, message = "QUANTITY_TOO_SMALL")
    Integer quantity;
}
