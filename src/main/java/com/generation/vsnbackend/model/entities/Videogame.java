package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Videogame extends BaseEntity {
    private String nameVideogame;
    private String softwareHouse;
    private String description;
    private String urlImage;
    private boolean preferred;
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "videogame", fetch= FetchType.EAGER)
    private List<Review> reviews=new ArrayList<>();

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
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


    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    //da 1 a 10
    public int getStarReviews() {
        int media=0;
        for (Review review : reviews) {
            media+=review.getNumberOfStar();
        }
        if(!reviews.isEmpty())
            media = media / reviews.size();

        return media;
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
}
