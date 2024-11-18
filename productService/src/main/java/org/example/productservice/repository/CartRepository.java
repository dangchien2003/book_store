package org.example.productservice.repository;

import org.example.productservice.entity.Cart;

import java.util.List;

public interface CartRepository {
    boolean create(Cart cart);

    void deleteAll(String user, List<Long> bookIds);

}
