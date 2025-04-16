package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.bot.MaintenanceBot;
import ru.service.maintenance.converters.DistrictConverter;
import ru.service.maintenance.converters.StreetConverter;
import ru.service.maintenance.converters.WorkSiteConverter;
import ru.service.maintenance.dtos.DistrictDto;
import ru.service.maintenance.dtos.StreetDto;
import ru.service.maintenance.dtos.WorkSiteDto;
import ru.service.maintenance.entyties.District;
import ru.service.maintenance.entyties.Street;
import ru.service.maintenance.entyties.WorkSite;
import ru.service.maintenance.exceptions.InvalidParamsException;
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.repositories.WorkSiteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkSiteService {
    private final WorkSiteRepository workSiteRepository;
    private final StreetService streetService;
    private final InstallationService installationService;
    private final ManufactureService manufactureService;
    private final DistrictService districtService;
    private final DistrictConverter districtConverter;
    private final StreetConverter streetConverter;
    private final WorkSiteConverter workSiteConverter;

    public List<WorkSite> FindAll() {
        return workSiteRepository.findAll();
    }

    public Optional<WorkSite> FindById(Long id) {
        return workSiteRepository.findById(id);
    }

    public void deleteById(Long id) {
        workSiteRepository.deleteById(id);
    }

    public Collection<WorkSite> findAll() {
        return workSiteRepository.findAll();
    }

    public List<WorkSite> findAllByStreetId(List<Long> idStreet) {
        if (idStreet == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        return workSiteRepository.findAllByStreetId(idStreet);
    }


    public void createNewWorkSite(WorkSiteDto workSiteDto) {
        WorkSite workSite = new WorkSite();
        workSite.setStreets(streetService.FindById(workSiteDto.getStreetId()).get());
        workSite.setFrame(workSiteDto.getFrame());
        workSite.setHouse(workSiteDto.getHouse());
        workSite.setInstallations(installationService.FindById(workSiteDto.getInstallationId()).get());
        workSite.setManufactures(manufactureService.FindById(workSiteDto.getManufactureId()).get());
        workSiteRepository.save(workSite);
    }

    @Transactional
    public void changeWorkSite(String title, Long id) {
        if (title == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            workSiteRepository.changeWorkSite(title, id);
            workSiteRepository.changeUpdateAt(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка изменения улицы. Улица " + id + "не существует");
        }

    }

    @Transactional
    public void changeAtWork(String atWork, Long id) {
        if (atWork == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            if (atWork.equals("true")) {
                workSiteRepository.changeAtWork(true, id);
            }
            if (atWork.equals("false")) {
                workSiteRepository.changeAtWork(false, id);
            }
            workSiteRepository.changeUpdateAt(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка статуса обЪекта. Объект с ID " + id + "не существует");
        }
    }

    @Transactional
    public void changeDone(String done, Long id) {
        if (done == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            if (done.equals("true")) {
                workSiteRepository.changeDone(true, id);
            }
            if (done.equals("false")) {
                workSiteRepository.changeDone(false, id);
            }
            workSiteRepository.changeUpdateAt(id);
            changeAtWork("false", id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка статуса обЪекта. Объект с ID " + id + "не существует");
        }
    }
    @Transactional
    public void changeNoDone(String noDone, Long id) {
        if (noDone == null || id == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            if (noDone.equals("true")) {
                workSiteRepository.changeNoDone(true, id);
            }
            if (noDone.equals("false")) {
                workSiteRepository.changeNoDone(false, id);
            }
            workSiteRepository.changeUpdateAt(id);
            changeAtWork("false", id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка статуса обЪекта. Объект с ID " + id + "не существует");
        }
    }

    public void updateFlagsMonthly() {
        List<WorkSite> workSites = workSiteRepository.findAll();
        for (WorkSite workSite : workSites) {
            if (!workSite.isDone()) {
                workSite.setNoDone(true);
                workSite.setDone(false);
            }
            workSiteRepository.saveAll(workSites);
        }
    }

    public void updateWorkSite(Long id, WorkSiteDto updateDto) {
        WorkSite workSite = workSiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkSite not found"));

        // Обновляем только необходимые поля
        if (updateDto.getAtWork() != null) {
            workSite.setAtWork(updateDto.getAtWork());
            if (updateDto.getAtWork()) {
                workSite.setDone(false);
                workSite.setNoDone(false);
            }
        }

        if (updateDto.getDone() != null) {
            workSite.setDone(updateDto.getDone());
            if (updateDto.getDone()) {
                workSite.setAtWork(false);
                workSite.setNoDone(false);
            }
        }

        if (updateDto.getNoDone() != null) {
            workSite.setNoDone(updateDto.getNoDone());
        }

        if (updateDto.getUserAtWork() != null) {
            workSite.setUserAtWork(updateDto.getUserAtWork());
        }

        workSiteRepository.save(workSite);
    }
}