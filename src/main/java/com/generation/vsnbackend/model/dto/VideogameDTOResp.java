package com.generation.vsnbackend.model.dto;

public class VideogameDTOResp {


    private Long id;
    private String nameVideogame;
    private String softwareHouse;
    private String description;
    private String urlImage;
    private boolean preferred;
    private String releaseDate;
    private int starReviews;

    private Long ProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getStarReviews() {
        return starReviews;
    }

    public void setStarReviews(int starReviews) {
        this.starReviews = starReviews;
    }

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

    public boolean isPreferred(boolean preferred) {
        return this.preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getProfileId() {
        return ProfileId;
    }

    public void setProfileId(Long profileId) {
        ProfileId = profileId;
    }
}
