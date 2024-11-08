package org.example.identityservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.utils.Validate;
import org.example.identityservice.validator.IsEmail;
import org.example.identityservice.validator.NotSpaceFirstAndEnd;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = "DATA_BLANK")
    @IsEmail()
    String email;

    @NotBlank(message = "DATA_BLANK")
    @Length(min = Validate.minLengthPassword, message = "Minimum password length: " + Validate.minLengthPassword + " characters")
    @Length(max = Validate.maxLengthPassword, message = "Maximum password length: " + Validate.maxLengthPassword + " characters")
    @NotSpaceFirstAndEnd(message = "LEAD_AND_TRAILING_SPACES")
    String password;
}
