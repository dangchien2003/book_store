package org.example.productservice.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookSize {
    @Min(value = 1, message = "Width must be greater than 1")
    int width;

    @Min(value = 1, message = "Wide must be greater than 1")
    int wide;

    @Min(value = 1, message = "Height must be greater than 1")
    int height;
}
