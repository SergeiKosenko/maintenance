package ru.service.maintenance.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
import ru.service.maintenance.exceptions.ResourceNotFoundException;
import ru.service.maintenance.services.DistrictService;
import ru.service.maintenance.services.RegionesService;
import ru.service.maintenance.services.StreetService;
import ru.service.maintenance.services.WorkSiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/worksites")
@RequiredArgsConstructor
public class WorkSiteController {
    private final WorkSiteService workSiteService;
    private final WorkSiteConverter workSiteConverter;
    private final StreetService streetService;
    private final StreetConverter streetConverter;
    private final DistrictService districtService;
    private final DistrictConverter districtConverter;


    @GetMapping("/all")
    public List<WorkSiteDto> getAllWorkSite() {
        return workSiteService.findAll().stream().map(workSiteConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/region/{regionId}")
    public List<WorkSiteDto> getWorkSiteByUserRegiones(@PathVariable Long regionId) {
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
        return workSiteService.findAllByStreetId(IdStreet).stream()
                .filter(workSite -> !workSite.isDone()) // Фильтруем только незавершенные работы
                .map(workSiteConverter::entityToDto) // Преобразуем в DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/regionnodone/{regionId}")
    public List<WorkSiteDto> getWorkSiteByUserRegionesNoDone(@PathVariable Long regionId) {
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
        return workSiteService.findAllByStreetId(IdStreet).stream()
                .filter(WorkSite::isNoDone) // Фильтруем только незавершенные работы
                .map(workSiteConverter::entityToDto) // Преобразуем в DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/streetid/{streetId}")
    public List<WorkSiteDto> getWorkSiteByStreetId(@PathVariable List<Long> streetId) {
        List<WorkSiteDto> list = new ArrayList<>();
        for (WorkSite workSite : workSiteService.findAllByStreetId(streetId)) {
            WorkSiteDto workSiteDto = workSiteConverter.entityToDto(workSite);
            list.add(workSiteDto);
        }
        return list;
    }

    @GetMapping("/{id}")
    public WorkSiteDto getWorkSiteById(@PathVariable Long id) {
        return workSiteConverter.entityToDto(workSiteService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Объект с ID: " + id + " не найден")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewWorkSite(@RequestBody WorkSiteDto workSiteDto) {
        workSiteService.createNewWorkSite(workSiteDto);
    }

//    @PatchMapping("/{id}")
//    public void changeWorkSite(@RequestParam String title, @PathVariable Long id) {
//        workSiteService.changeWorkSite(title, id);
//    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateWorkSite(
            @PathVariable Long id,
            @RequestBody WorkSiteDto updateDto) {
        log.info("Received PATCH for id {} with data: {}", id, updateDto);
        workSiteService.updateWorkSite(id, updateDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public void deleteWorkSiteById(@PathVariable Long id) {
        workSiteService.deleteById(id);
    }


    @PatchMapping("/atwork/{id}")
    public void changeAtWork(@RequestParam String atWork, @PathVariable Long id) {
        workSiteService.changeAtWork(atWork, id);
    }

    @PatchMapping("/done/{id}")
    public void changeDone(@RequestParam String done, @PathVariable Long id) {
        workSiteService.changeDone(done, id);
    }

    @PatchMapping("/nodone/{id}")
    public void changeNoDone(@RequestParam String noDone, @PathVariable Long id) {
        workSiteService.changeNoDone(noDone, id);
    }
}
