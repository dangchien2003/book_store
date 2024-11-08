package org.example.identityservice.repository;

import org.example.identityservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    @Query("SELECT COUNT(p) FROM Permission p WHERE p.name IN :names")
    int countByNames(@Param("names") List<String> names);
}
