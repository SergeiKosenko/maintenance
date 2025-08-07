package ru.service.maintenance.api;

public class StreetDto {
    private Long id;
    private Long districtId;
    private String title;
    private String districtTitle;
    private String regionesTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public String getRegionesTitle() {
        return regionesTitle;
    }

    public void setRegionesTitle(String regionesTitle) {
        this.regionesTitle = regionesTitle;
    }

    public StreetDto() {
    }

    public StreetDto(Long id, Long districtId, String title, String districtTitle, String regionesTitle) {
        this.id = id;
        this.districtId = districtId;
        this.title = title;
        this.districtTitle = districtTitle;
        this.regionesTitle = regionesTitle;
    }
}
