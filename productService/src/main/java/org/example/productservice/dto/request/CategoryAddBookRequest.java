package org.example.productservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddBookRequest {
    @NotNull(message = "NOTFOUND_ID")
    Integer categoryId;

    @Size(min = 1, message = "LIST_BOOK_EMPTY")
    Set<Long> bookIds;
}
