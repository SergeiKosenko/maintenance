package ru.service.maintenance.converters;

import org.springframework.stereotype.Component;
import ru.service.maintenance.dtos.WorkSiteDto;
import ru.service.maintenance.entyties.WorkSite;

@Component
public class WorkSiteConverter {
    public WorkSiteDto entityToDto(WorkSite p) {
        WorkSiteDto workSiteDto = new WorkSiteDto();

        workSiteDto.setId(p.getId());
        workSiteDto.setStreetId(p.getStreets().getId());
        workSiteDto.setDistrictTitle(p.getStreets().getDistrict().getTitle());
        workSiteDto.setRegionId(p.getStreets().getDistrict().getRegiones().getId());
        workSiteDto.setStreetTitle(p.getStreets().getTitle());
        workSiteDto.setHouse(p.getHouse());
        workSiteDto.setFrame(p.getFrame());
        workSiteDto.setManufactureId(p.getManufactures().getId());
        workSiteDto.setManufactureTitle(p.getManufactures().getTitle());
        workSiteDto.setInstallationId(p.getInstallations().getId());
        workSiteDto.setInstallationTitle(p.getInstallations().getTitle());
        workSiteDto.setAtWork(p.isAtWork());
        workSiteDto.setDone(p.isDone());
        workSiteDto.setNoDone(p.isNoDone());
        workSiteDto.setUserAtWork(p.getUserAtWork());
        return workSiteDto;
    }
}
