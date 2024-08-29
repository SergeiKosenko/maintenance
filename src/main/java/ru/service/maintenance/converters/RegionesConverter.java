package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.RegionesDto;
import ru.service.maintenance.entyties.Regiones;

@Component
public class RegionesConverter {
    public RegionesDto entityToDto(Regiones p) {
       RegionesDto regionesDto = new RegionesDto();
        regionesDto.setId(p.getId());
        regionesDto.setTitle(p.getTitle());
       return regionesDto;
    }
}
