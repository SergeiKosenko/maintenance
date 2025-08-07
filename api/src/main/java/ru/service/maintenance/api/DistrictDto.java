package ru.service.maintenance.api;

public class DistrictDto {
    private Long id;
    private Long regionesId;
    private String title;
    private String regionesTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(Long regionesId) {
        this.regionesId = regionesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegionesTitle() {
        return regionesTitle;
    }

    public void setRegionesTitle(String regionesTitle) {
        this.regionesTitle = regionesTitle;
    }

    public DistrictDto() {
    }

    public DistrictDto(Long id, Long regionesId, String title, String regionesTitle) {
        this.id = id;
        this.regionesId = regionesId;
        this.title = title;
        this.regionesTitle = regionesTitle;
    }
}
