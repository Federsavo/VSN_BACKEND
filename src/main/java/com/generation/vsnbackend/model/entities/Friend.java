package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

@Entity
public class Friend extends BaseEntity
{

    @OneToOne(mappedBy = "friend" )
    private User user;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "profile_follower_id")
    private Profile profile_follower;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.user.setId(user.getId());
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile_follower() {
        return profile_follower;
    }

    public void setProfile_follower(Profile profile_follower) {
        this.profile_follower = profile_follower;
    }
}
