package org.example.productservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookCreationRequest {
    @NotBlank(message = "DATA_BLANK")
    String name;

    @Min(value = 1, message = "Reprint edition must be greater than 1")
    int reprintEdition;

    @Min(value = 1, message = "Price must be greater than 1")
    int price;

    @Min(value = 1, message = "Cost price must be greater than 1")
    int costPrice;

    @Min(value = 0, message = "Discount must be greater than 0")
    @Max(value = 100, message = "Discount must be less than 100")
    int discount;

    @Min(value = 0, message = "Page count must be greater than 0")
    int pageCount;

    @Min(value = 0, message = "Page count must be greater than 0")
    int availableQuantity;

    BookSize size;
    String statusCode;
    Integer publisherId;
    Long authorId;
    String description;
}
