package ru.service.maintenance.core.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.api.InstallationDto;
import ru.service.maintenance.core.entyties.Installation;

@Component
public class InstallationConverter {
    public InstallationDto entityToDto(Installation p) {

        InstallationDto installationDto = new InstallationDto();
        installationDto.setId(p.getId());
        installationDto.setTitle(p.getTitle());

        return installationDto;
    }
}
