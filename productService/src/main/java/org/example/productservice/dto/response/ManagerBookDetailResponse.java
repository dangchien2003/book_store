package org.example.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.BookSize;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagerBookDetailResponse extends BaseBookResponse {
    int authorId;
    int publisherId;
    int reprintEdition;
    List<String> childImages;
    String otherImage;
    String statusCode;
    int availableQuantity;
    int pageCount;
    String size;
    BookSize bookSize;
}


