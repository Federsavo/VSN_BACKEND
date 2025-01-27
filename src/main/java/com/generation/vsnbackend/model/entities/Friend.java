package com.generation.vsnbackend.model.entities;

import jakarta.persistence.*;

@Entity
public class Friend extends BaseEntity
{
//
//    @OneToOne(mappedBy = "friend" )
//    private User user;

    @ManyToOne
    @JoinColumn(name = "profile_following_id")
    private Profile profile_following;

    @ManyToOne
    @JoinColumn(name = "profile_follower_id")
    private Profile profile_follower;

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//        this.user.setId(user.getId());
//    }


    public Profile getProfile_following() {
        return profile_following;
    }

    public void setProfile_following(Profile profile_following) {
        this.profile_following = profile_following;
    }

    public Profile getProfile_follower() {
        return profile_follower;
    }

    public void setProfile_follower(Profile profile_follower) {
        this.profile_follower = profile_follower;
    }
}
