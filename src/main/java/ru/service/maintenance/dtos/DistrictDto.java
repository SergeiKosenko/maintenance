package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistrictDto {
    private Long id;
    private Long regionesId;
    private String title;
    private String regionesTitle;

}
