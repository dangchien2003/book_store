package org.example.orderservice.repository;

import org.example.orderservice.dto.response.ItemDataProvinceResponse;

import java.util.List;

public interface ProvinceRepository {
    void createAll(List<ItemDataProvinceResponse> data);
}
