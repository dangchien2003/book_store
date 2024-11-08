package org.example.identityservice.specifications;

import org.example.identityservice.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasPhoneNumber(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("phone"), phone);
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<User> findByPhoneOrEmail(String phone, String email) {
        if (phone != null) {
            return Specification.where(hasPhoneNumber(phone)).or(hasEmail(email));
        } else {
            return Specification.where(hasEmail(email));
        }
    }

}
