package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Videogame extends BaseEntity {
    private String nameVideogame;
    private String publishers;
    private String developers;
    private boolean preferred;
    private LocalDate releaseDate;
    private Long appId;
    private String genre;

    private int numberOfStars;
    private String iconImgUrl;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;


    @OneToMany(mappedBy = "videogame", fetch= FetchType.EAGER)
    private List<Review> reviews=new ArrayList<>();

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean prefer) {
        this.preferred = prefer;
    }

    public String getNameVideogame() {
        return nameVideogame;
    }

    public void setNameVideogame(String nameVideogame) {
        this.nameVideogame = nameVideogame;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReviews(Review review) {
        reviews.add(review);
    }

    public Long getAppId()
    {
        return appId;
    }

    public void setAppId(Long appId)
    {
        this.appId = appId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNumberOfStars()
    {
        return numberOfStars;
    }

    public void setNumberOfStars(int numberOfStars)
    {
        this.numberOfStars = numberOfStars;
    }

    public String getIconImgUrl()
    {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl)
    {
        this.iconImgUrl = iconImgUrl;
    }

    @Override
    public String toString()
    {
        return
                "nameVideogame:" + nameVideogame + "\n" +
                        "publishers:" + publishers + "\n" +
                        "developers:" + developers + "\n" +
                        "preferred:" + preferred + "\n" +
                        "releaseDate:" + releaseDate + "\n" +
                        "appId:" + appId + "\n" +
                        "genre:" + genre + "\n" +
                        "numberOfStars:" + numberOfStars + "\n" +
                        "iconImgUrl:" + iconImgUrl + "\n" +
                        "profile:" + profile + "\n" +
                        "reviews:" + reviews + "\n";
    }
}
