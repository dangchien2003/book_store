package org.example.orderservice.configuration;

import org.example.orderservice.dto.response.ItemDataProvinceResponse;
import org.example.orderservice.dto.response.ProvinceClientResponse;
import org.example.orderservice.repository.CommuneRepository;
import org.example.orderservice.repository.DistrictRepository;
import org.example.orderservice.repository.ProvinceRepository;
import org.example.orderservice.repository.httpClient.AddressClient;

//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressRunner {
    AddressClient addressClient;
    ProvinceRepository provinceRepository;
    DistrictRepository districtRepository;
    CommuneRepository communeRepository;

    //    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        ProvinceClientResponse provinceClientResponse = addressClient.getAllProvince();
//        provinceRepository.createAll(provinceClientResponse.getData());

        for (ItemDataProvinceResponse item : provinceClientResponse.getData()) {
            ProvinceClientResponse districts = addressClient.getAllDistrict(String.format("%02d", item.getId()));
//            districtRepository.createAll(districts.getData(), item.getId());
            for (ItemDataProvinceResponse item1 : districts.getData()) {
                ProvinceClientResponse communes = addressClient.getAllCommune(String.format("%03d", item1.getId()));
//                communeRepository.createAll(communes.getData(), item1.getId());
            }

        }
    }
}
