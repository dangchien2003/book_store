package org.example.orderservice.repository;

import org.example.orderservice.dto.response.ItemDataProvinceResponse;

import java.util.List;

public interface CommuneRepository {
    void createAll(List<ItemDataProvinceResponse> data, int district);

    boolean existById(int commune);

}
