package com.generation.vsnbackend.model.dto;

public class FriendSummaryDTO {

    private Long id;
    private String steamId;

    private int followersCount;
    private int followingCount;

    private Long lastPlayedVideogameAppId;

    private String profileName;
//    private String steamName;
//    private String playstationName;
//    private String xboxName;

    private Long profileID;
    private Long profileImgId;
    private Long profileBackdropImgId;

    private String lastPlayedGameImgUrl;
    private String lastPlayedGameName;

    public Long getProfileID()
    {
        return profileID;
    }

    public void setProfileID(Long profileID)
    {
        this.profileID = profileID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public Long getLastPlayedVideogameAppId() {
        return lastPlayedVideogameAppId;
    }

    public void setLastPlayedVideogameAppId(Long lastPlayedVideogameAppId) {
        this.lastPlayedVideogameAppId = lastPlayedVideogameAppId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }


    public Long getProfileImgId() {
        return profileImgId;
    }

    public void setProfileImgId(Long profileImgId) {
        this.profileImgId = profileImgId;
    }

    public Long getProfileBackdropImgId() {
        return profileBackdropImgId;
    }

    public void setProfileBackdropImgId(Long profileBackdropImgId) {
        this.profileBackdropImgId = profileBackdropImgId;
    }

    public String getLastPlayedGameImgUrl() {
        return lastPlayedGameImgUrl;
    }

    public void setLastPlayedGameImgUrl(String lastPlayedGameImgUrl) {
        this.lastPlayedGameImgUrl = lastPlayedGameImgUrl;
    }

    public String getLastPlayedGameName() {
        return lastPlayedGameName;
    }

    public void setLastPlayedGameName(String lastPlayedGameName) {
        this.lastPlayedGameName = lastPlayedGameName;
    }
}
