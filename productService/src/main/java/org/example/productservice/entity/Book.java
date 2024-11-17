package org.example.productservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.BookSize;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;

import java.util.Objects;
import java.util.StringJoiner;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Book extends EntityWithTimestamps {
    Long id;
    String name;
    int reprintEdition;
    int price;
    int costPrice;
    int discount;
    Integer publisherId;
    Long authorId;
    int pageCount;
    String size;
    int availableQuantity;
    String description;
    String mainImage;
    String otherImage;
    String statusCode;

    static String sizeSeparator = "x";
    static String[] sizeOrder = {"width", "wide", "height"};

    public void setSize(BookSize bookSize) {

        if (bookSize.getWidth() == 0 || bookSize.getWide() == 0 || bookSize.getHeight() == 0)
            throw new AppException(ErrorCode.INVALID_DATA);

        StringJoiner joiner = new StringJoiner(sizeSeparator);
        int count = 0;
        for (String key : sizeOrder) {
            if (key.equals("width"))
                joiner.add(String.valueOf(bookSize.getWidth()));
            else if (key.equals("wide"))
                joiner.add(String.valueOf(bookSize.getWide()));
            else if (key.equals("height"))
                joiner.add(String.valueOf(bookSize.getHeight()));
            else
                throw new AppException(ErrorCode.INVALID_DATA);
            count++;
        }
        if (count != 3)
            throw new AppException(ErrorCode.INVALID_DATA);

        this.size = joiner.toString();
    }

    public BookSize getBookSize() {
        return getBookSize(this.size);
    }

    public static BookSize getBookSize(String size) {
        if (Objects.isNull(size))
            return new BookSize();

        String[] splitSize = size.split(sizeSeparator);
        BookSize bookSize = new BookSize();

        try {
            for (int i = 0; i < splitSize.length; i++) {
                int value = Integer.parseInt(splitSize[i]);
                switch (sizeOrder[i]) {
                    case "width" -> bookSize.setWidth(value);
                    case "wide" -> bookSize.setWide(value);
                    case "height" -> bookSize.setHeight(value);
                    default -> log.warn("Unknown key");
                }
            }
        } catch (NumberFormatException e) {
            log.error("Book Error:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return bookSize;
    }
}
