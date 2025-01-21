package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    private PostType whatIs;//post di che tipo?
    private Date pubblicationDate;
    private String content;
    private int nLike;


    @OneToMany(mappedBy = "post", fetch= FetchType.EAGER)
    private List<Comment> comments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;


    public PostType getWhatIs() {
        return whatIs;
    }

    public void setWhatIs(PostType whatIs) {
        this.whatIs = whatIs;
    }

    public Date getPubblicationDate() {
        return pubblicationDate;
    }

    public void setPubblicationDate(Date pubblicationDate) {
        this.pubblicationDate = pubblicationDate;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getnLike() {
        return nLike;
    }

    public void setnLike(int nLike) {
        this.nLike = nLike;
    }
}
