package ru.service.maintenance.api;

public class ManufactureDto {
    private Long id;
    private Long idFirm;
    private String firm;
    private String title;
    private String uri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFirm() {
        return idFirm;
    }

    public void setIdFirm(Long idFirm) {
        this.idFirm = idFirm;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ManufactureDto() {
    }

    public ManufactureDto(Long id, Long idFirm, String firm, String title, String uri) {
        this.id = id;
        this.idFirm = idFirm;
        this.firm = firm;
        this.title = title;
        this.uri = uri;
    }
}
