package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment extends BaseEntity{
    private String theComment;
    private int nLike;
    private String author;
    private LocalDateTime pubblicationDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public String getTheComment() {
        return theComment;
    }

    public void setTheComment(String theComment) {
        this.theComment = theComment;
    }

    public int getnLike() {
        return nLike;
    }

    public void setnLike(int nLike) {
        this.nLike = nLike;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getPubblicationDate() {
        return pubblicationDate;
    }

    public void setPubblicationDate(LocalDateTime pubblicationDate) {
        this.pubblicationDate = pubblicationDate;
    }
}
