package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.InstallationDto;
import ru.service.maintenance.entyties.Installation;

@Component
public class InstallationConverter {
    public InstallationDto entityToDto(Installation p) {

        InstallationDto installationDto = new InstallationDto();
        installationDto.setId(p.getId());
        installationDto.setTitle(p.getTitle());

        return installationDto;
    }
}
