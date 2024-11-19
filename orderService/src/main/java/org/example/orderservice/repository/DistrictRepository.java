package org.example.orderservice.repository;

import org.example.orderservice.dto.response.ItemDataProvinceResponse;

import java.util.List;

public interface DistrictRepository {
    void createAll(List<ItemDataProvinceResponse> data, int province);

}
