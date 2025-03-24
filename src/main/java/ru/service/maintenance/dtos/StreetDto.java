package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StreetDto {
    private Long id;
    private Long districtId;
    private String title;
    private String districtTitle;
    private String regionesTitle;
}
