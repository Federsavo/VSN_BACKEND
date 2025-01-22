package com.generation.vsnbackend.model.dto;

import com.generation.vsnbackend.model.entities.PostType;

import java.util.Date;

public class PostDTOReq {

    private PostType whatIs;//post di che tipo?
    private String content;
    private int nLike;
    private String image;
    private Long profileId;

    public PostType getWhatIs() {
        return whatIs;
    }

    public void setWhatIs(PostType whatIs) {
        this.whatIs = whatIs;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
