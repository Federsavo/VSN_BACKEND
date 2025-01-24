package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    private PostType whatIs;//post di che tipo?
    private LocalDateTime publicationDate;
    private String content;
    private int nLike;
    private String image;


    @OneToMany(mappedBy = "post", fetch= FetchType.EAGER)
    private List<Comment> comments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public PostType getWhatIs() {
        return whatIs;
    }

    public void setWhatIs(PostType whatIs) {
        this.whatIs = whatIs;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime pubblicationDate) {
        this.publicationDate = pubblicationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getnLike() {
        return nLike;
    }

    public void setnLike(int nLike) {
        this.nLike = nLike;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
