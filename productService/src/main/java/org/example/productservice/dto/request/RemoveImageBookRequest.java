package org.example.productservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RemoveImageBookRequest {
    @NotNull(message = "NOTFOUND_ID")
    Long bookId;

    //    @NotNull(message = "DATA_BLANK")
//    @Min(value = 0, message = "Thứ tự không hợp lệ")
    Integer index;

    @NotNull(message = "DATA_BLANK")
    String type;
}
