package ru.service.maintenance.core.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.api.ManufactureDto;
import ru.service.maintenance.core.entyties.Manufacture;

@Component
public class ManufactureConverter {
    public ManufactureDto entityToDto(Manufacture p) {

        ManufactureDto manufactureDto = new ManufactureDto();
        manufactureDto.setId(p.getId());
        manufactureDto.setIdFirm(p.getFirm().getId());
        manufactureDto.setFirm(p.getFirm().getTitle());
        manufactureDto.setTitle(p.getTitle());
        manufactureDto.setUri(p.getUri());

        return manufactureDto;
    }
}

