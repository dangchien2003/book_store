package org.example.identityservice.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.identityservice.enums.TokenStatus;

@Converter(autoApply = true)
public class TokenStatusConverter implements AttributeConverter<TokenStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TokenStatus tokenStatus) {
        if (tokenStatus == null) {
            return null;
        }
        return tokenStatus.getStatus();
    }

    @Override
    public TokenStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TokenStatus.fromStatus(dbData);
    }
}