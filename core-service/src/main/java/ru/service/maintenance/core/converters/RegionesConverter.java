package ru.service.maintenance.core.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.api.RegionesDto;
import ru.service.maintenance.core.entyties.Regiones;

@Component
public class RegionesConverter {
    public RegionesDto entityToDto(Regiones p) {
       RegionesDto regionesDto = new RegionesDto();
        regionesDto.setId(p.getId());
        regionesDto.setTitle(p.getTitle());
       return regionesDto;
    }
}
