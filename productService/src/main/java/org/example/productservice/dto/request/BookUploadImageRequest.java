package org.example.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class BookUploadImageRequest {
    @NotNull(message = "Không tìm thấy ảnh")
    private MultipartFile file;

    @NotBlank(message = "BLANK_DATA")
    String bookId;

    @NotBlank(message = "BLANK_DATA")
    String type;
}
