package org.example.productservice.repository;

import org.example.productservice.dto.response.PublisherResponse;
import org.example.productservice.entity.Publisher;

import java.util.List;

public interface PublisherRepository {
    Integer create(Publisher publisher);

    int update(Publisher publisher);

    Publisher findById(Integer id) throws Exception;

    boolean existById(Integer id);

    List<PublisherResponse> findAllOrderByName(int numberPage, int pageSize) throws Exception;

    List<PublisherResponse> findAllByName(String name, int numberPage, int pageSize) throws Exception;
}
