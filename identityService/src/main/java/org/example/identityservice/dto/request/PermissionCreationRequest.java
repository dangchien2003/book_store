package org.example.identityservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionCreationRequest {

    @NotBlank(message = "FIELD_BLANK")
    @Size(min = 5, message = "The name field length must be greater than 5")
    String name;

    @NotBlank(message = "FIELD_BLANK")
    String description;
}
