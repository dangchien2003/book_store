package org.example.productservice.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public enum PrefixCache {
    BOOK("BOOK", "BOOK_");
    String name;
    String value;
}
