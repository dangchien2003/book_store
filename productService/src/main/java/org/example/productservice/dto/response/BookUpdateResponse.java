package org.example.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookUpdateResponse {
    String name;
    int reprintEdition;
    int price;
    int costPrice;
    int discount;
    int publisherId;
    long authorId;
    int pageCount;
    int width;
    int wide;
    int height;
    int availableQuantity;
    String description;
    String statusCode;
}
