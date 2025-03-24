package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.ManufactureDto;
import ru.service.maintenance.entyties.Manufacture;

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

