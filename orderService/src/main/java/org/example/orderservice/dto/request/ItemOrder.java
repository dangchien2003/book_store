package org.example.orderservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ItemOrder {
    @NotNull(message = "DATA_BLANK")
    Long bookId;

    @NotNull(message = "DATA_BLANK")
    @Min(value = 1, message = "PRODUCT_QUANTITY_IS_TOO_SMALL")
    @Max(value = 10, message = "PRODUCT_QUANTITY_IS_TOO_LARGE")
    Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrder itemOrder = (ItemOrder) o;
        return bookId == itemOrder.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }

    @Override
    public String toString() {
        return "ItemOrder{" +
                "bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
