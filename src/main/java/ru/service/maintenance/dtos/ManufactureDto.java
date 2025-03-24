package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManufactureDto {
    private Long id;
    private Long idFirm;
    private String firm;
    private String title;
    private String uri;

}
