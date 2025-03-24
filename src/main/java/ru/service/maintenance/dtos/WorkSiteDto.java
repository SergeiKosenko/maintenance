package ru.service.maintenance.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkSiteDto {
    private Long id;
    private Long streetId;
    private String districtTitle;
    private Long regionId;
    private String streetTitle;
    private String house;
    private String frame;
    private Long manufactureId;
    private String manufactureTitle;
    private Long installationId;
    private String installationTitle;
    private Boolean atWork;
    private Boolean done;
    private Boolean noDone;
    private String userAtWork;
}
