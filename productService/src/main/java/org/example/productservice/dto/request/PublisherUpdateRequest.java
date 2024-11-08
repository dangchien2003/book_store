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
public class PublisherUpdateRequest {
    @NotNull(message = "NOTFOUND_ID")
    Integer id;
    @NotNull(message = "INVALID_NAME")
    String name;
    String website;
}
