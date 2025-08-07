package ru.service.maintenance.api;

public class WorkSiteDto {
    private Long id;
    private Long streetId;
    private String districtTitle;
    private Long districtId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getStreetTitle() {
        return streetTitle;
    }

    public void setStreetTitle(String streetTitle) {
        this.streetTitle = streetTitle;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public Long getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Long manufactureId) {
        this.manufactureId = manufactureId;
    }

    public String getManufactureTitle() {
        return manufactureTitle;
    }

    public void setManufactureTitle(String manufactureTitle) {
        this.manufactureTitle = manufactureTitle;
    }

    public Long getInstallationId() {
        return installationId;
    }

    public void setInstallationId(Long installationId) {
        this.installationId = installationId;
    }

    public String getInstallationTitle() {
        return installationTitle;
    }

    public void setInstallationTitle(String installationTitle) {
        this.installationTitle = installationTitle;
    }

    public Boolean getAtWork() {
        return atWork;
    }

    public void setAtWork(Boolean atWork) {
        this.atWork = atWork;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getNoDone() {
        return noDone;
    }

    public void setNoDone(Boolean noDone) {
        this.noDone = noDone;
    }

    public String getUserAtWork() {
        return userAtWork;
    }

    public void setUserAtWork(String userAtWork) {
        this.userAtWork = userAtWork;
    }

    public WorkSiteDto() {
    }

    public WorkSiteDto(Long id, Long streetId, String districtTitle, Long districtId, Long regionId, String streetTitle, String house, String frame, Long manufactureId, String manufactureTitle, Long installationId, String installationTitle, Boolean atWork, Boolean done, Boolean noDone, String userAtWork) {
        this.id = id;
        this.streetId = streetId;
        this.districtTitle = districtTitle;
        this.districtId = districtId;
        this.regionId = regionId;
        this.streetTitle = streetTitle;
        this.house = house;
        this.frame = frame;
        this.manufactureId = manufactureId;
        this.manufactureTitle = manufactureTitle;
        this.installationId = installationId;
        this.installationTitle = installationTitle;
        this.atWork = atWork;
        this.done = done;
        this.noDone = noDone;
        this.userAtWork = userAtWork;
    }
}
