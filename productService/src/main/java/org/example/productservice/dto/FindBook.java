package org.example.productservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class FindBook {
    String name;
    List<Long> bookIds;
    Integer publisher;
    Long author;
    int numberPage;
    int pageSize;
}
