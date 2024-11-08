package org.example.identityservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.enums.UserStatus;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends EntityWithTimestamps {
    @Id
    String uid;

    @NotNull
    String email;

    String name;

    @NotNull
    String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    UserStatus statusCode;

    @ManyToOne
    Role role;
}
