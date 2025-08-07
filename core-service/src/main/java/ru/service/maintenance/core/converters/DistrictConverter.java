package ru.service.maintenance.core.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.api.DistrictDto;
import ru.service.maintenance.core.entyties.District;


@Component
public class DistrictConverter {
    public DistrictDto entityToDto(District p) {
        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(p.getId());
        districtDto.setTitle(p.getTitle());
        districtDto.setRegionesTitle(p.getRegiones().getTitle());
        districtDto.setRegionesId(p.getRegiones().getId());
        return districtDto;
    }


}
