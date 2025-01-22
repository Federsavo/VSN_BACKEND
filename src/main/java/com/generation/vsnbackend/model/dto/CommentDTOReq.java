package com.generation.vsnbackend.model.dto;

public class CommentDTOReq {

    private String theComment;
    private int nLike;
    private String author;

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
}
