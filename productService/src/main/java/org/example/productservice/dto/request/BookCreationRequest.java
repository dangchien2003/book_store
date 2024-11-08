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
public class BookCreationRequest {
    @NotNull(message = "INVALID_NAME")
    String name;
    int reprintEdition;
    @Min(value = 1000, message = "Price must be greater than 1000")
    int price;
    int costPrice;
    int discount;
    int publisherId;
    long authorId;
    int pageCount;
    int availableQuantity;

    String description;
    String mainImage;
    String otherImage;
    String statusCode;

    @Min(value = 1, message = "Width must be greater than 1")
    int width;

    @Min(value = 1, message = "Wide must be greater than 1")
    int wide;

    @Min(value = 1, message = "Height must be greater than 1")
    int height;
}
