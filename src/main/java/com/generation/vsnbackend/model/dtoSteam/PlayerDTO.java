package com.generation.vsnbackend.model.dtoSteam;

import java.time.LocalDate;

public class PlayerDTO {

    private String personaName;
    private String profileUrl;
    private String avatar;
    private String avatarMedium;
    private String avatarFull;
    private LocalDate lastLogOff;
    private String realName;
    private String primaryClanId;
    private LocalDate timeCreated;
    private String locCountryCode;

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarMedium() {
        return avatarMedium;
    }

    public void setAvatarMedium(String avatarMedium) {
        this.avatarMedium = avatarMedium;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(String avatarFull) {
        this.avatarFull = avatarFull;
    }

    public LocalDate getLastLogOff() {
        return lastLogOff;
    }

    public void setLastLogOff(LocalDate lastLogOff) {
        this.lastLogOff = lastLogOff;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPrimaryClanId() {
        return primaryClanId;
    }

    public void setPrimaryClanId(String primaryClanId) {
        this.primaryClanId = primaryClanId;
    }

    public LocalDate getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDate timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getLocCountryCode() {
        return locCountryCode;
    }

    public void setLocCountryCode(String locCountryCode) {
        this.locCountryCode = locCountryCode;
    }


    //    "personaname": "Savoldi",
//         "profileurl": "https://steamcommunity.com/profiles/76561198254481613/",
//         "avatar": "https://avatars.steamstatic.com/3d8776dda28e84c0b4a0a4a5050e82e0380a0f09.jpg",
//         "avatarmedium": "https://avatars.steamstatic.com/3d8776dda28e84c0b4a0a4a5050e82e0380a0f09_medium.jpg",
//         "avatarfull": "https://avatars.steamstatic.com/3d8776dda28e84c0b4a0a4a5050e82e0380a0f09_full.jpg",
//         "lastlogoff": 1737401145,
//         "realname": "Federico Savoldi",
//         "primaryclanid": "103582791429521408",
//         "timecreated": 1444418001,
//         "loccountrycode": "IT"
}
