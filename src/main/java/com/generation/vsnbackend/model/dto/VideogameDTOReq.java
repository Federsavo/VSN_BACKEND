package com.generation.vsnbackend.model.dto;


public class VideogameDTOReq {

    private String nameVideogame;
    private String softwareHouse;
    private String description;
    private String urlImage;
    private boolean preferred;
    private String releaseDate;

    public String getNameVideogame() {
        return nameVideogame;
    }

    public void setNameVideogame(String nameVideogame) {
        this.nameVideogame = nameVideogame;
    }

    public String getSoftwareHouse() {
        return softwareHouse;
    }

    public void setSoftwareHouse(String softwareHouse) {
        this.softwareHouse = softwareHouse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


}
