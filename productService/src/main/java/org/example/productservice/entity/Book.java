package org.example.productservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends EntityWithTimestamps {
    Long id;
    String name;
    int reprintEdition;
    int price;
    int costPrice;
    int discount;
    int publisherId;
    long authorId;
    int pageCount;
    String size;
    int availableQuantity;
    String description;
    String mainImage;
    String otherImage;
    String statusCode;

    static String sizeSeparator = "x";
    static String[] sizeOrder = {"width", "wide", "height"};

    public void setSize(int width, int wide, int height) {

        if (width == 0 || wide == 0 || height == 0)
            throw new AppException(ErrorCode.INVALID_DATA);

        StringJoiner joiner = new StringJoiner(sizeSeparator);
        int count = 0;
        for (String key : sizeOrder) {
            if (key.equals("width"))
                joiner.add(String.valueOf(width));
            else if (key.equals("wide"))
                joiner.add(String.valueOf(wide));
            else if (key.equals("height"))
                joiner.add(String.valueOf(height));
            else
                throw new AppException(ErrorCode.INVALID_DATA);
            count++;
        }
        if (count != 3)
            throw new AppException(ErrorCode.INVALID_DATA);

        this.size = joiner.toString();
    }

    public Map<String, Integer> getSize() {
        if (Objects.isNull(this.size))
            return new HashMap<>();

        String[] splitSize = this.size.split(sizeSeparator);

        Map<String, Integer> result = new HashMap<>();
        result.put(sizeOrder[0], Integer.parseInt(splitSize[0]));
        result.put(sizeOrder[1], Integer.valueOf(splitSize[1]));
        result.put(sizeOrder[2], Integer.valueOf(splitSize[2]));

        return result;
    }
}
