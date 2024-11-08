package org.example.identityservice.repository;

import org.example.identityservice.entity.Token;
import org.example.identityservice.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsByTokenIdAndReject(String tokenId, TokenStatus status);
}
