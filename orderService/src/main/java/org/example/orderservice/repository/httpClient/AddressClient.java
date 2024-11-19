package org.example.orderservice.repository.httpClient;

import org.example.orderservice.dto.response.ProvinceClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address", url = "https://esgoo.net/api-tinhthanh")
public interface AddressClient {
    @GetMapping(value = "/1/0.htm")
    ProvinceClientResponse getAllProvince();

    @GetMapping(value = "/2/{province}.htm")
    ProvinceClientResponse getAllDistrict(@PathVariable(name = "province") String province);

    @GetMapping(value = "/3/{commune}.htm")
    ProvinceClientResponse getAllCommune(@PathVariable(name = "commune") String commune);
}
