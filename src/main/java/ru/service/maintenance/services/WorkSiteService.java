package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<WorkSiteDto> getWorkSiteByUserRegiones(Long regionId) {
        List<Long> IdStreet = getLongs(regionId);
        return findAllByStreetId(IdStreet).stream()
                .filter(workSite -> !workSite.isDone()) // Фильтруем только незавершенные работы
                .map(workSiteConverter::entityToDto) // Преобразуем в DTO
                .collect(Collectors.toList());
    }

    public List<WorkSiteDto> getWorkSiteByUserRegionesNoDone(Long regionId) {
        List<Long> IdStreet = getLongs(regionId);
        return findAllByStreetId(IdStreet).stream()
                .filter(WorkSite::isNoDone) // Фильтруем только незавершенные работы
                .map(workSiteConverter::entityToDto) // Преобразуем в DTO
                .collect(Collectors.toList()); // Собираем результат в список
    }

    private List<Long> getLongs(Long regionId) {
        // Получаем все районы для данного региона
        List<DistrictDto> districts = new ArrayList<>();
        for (District district : districtService.findAllByRegionesId(regionId)) {
            DistrictDto districtDto = districtConverter.entityToDto(district);
            districts.add(districtDto);
        }

        // Получаем все улицы для всех найденных районов
        List<Long> idDistrict = districts.stream()
                .map(DistrictDto::getId)
                .collect(Collectors.toList());
        List<StreetDto> streets = new ArrayList<>();
        for (Street street : streetService.findAllByDistrictId(idDistrict)) {
            StreetDto streetDto = streetConverter.entityToDto(street);
            streets.add(streetDto);
        }

        // Получаем все объекты для всех найденных улиц
        List<Long> IdStreet = streets.stream()
                .map(StreetDto::getId)
                .collect(Collectors.toList());
        return IdStreet;
    }
}