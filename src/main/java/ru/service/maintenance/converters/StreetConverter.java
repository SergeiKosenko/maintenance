package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.StreetDto;
import ru.service.maintenance.entyties.Street;

@Component
public class StreetConverter {
    public StreetDto entityToDto(Street p) {
        StreetDto streetDto = new StreetDto();

        streetDto.setId(p.getId());
        streetDto.setTitle(p.getTitle());
        streetDto.setDistrictTitle(p.getDistrict().getTitle());
        streetDto.setRegionesTitle(p.getDistrict().getRegiones().getTitle());
        return streetDto;
    }
}
