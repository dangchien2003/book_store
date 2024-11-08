package org.example.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends EntityWithTimestamps {
    @Id
    @NotNull
    String name;

    @NotNull
    boolean manager;

    String description;

    @ManyToMany
    Set<Permission> permissions;
}
