package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.entyties.District;

@Component
public class DistrictConverter {
    public DistrictDto entityToDto(District p) {
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(p.getId());
        districtDto.setTitle(p.getTitle());
        districtDto.setRegionesTitle(p.getRegiones().getTitle());
        return districtDto;
    }
}
