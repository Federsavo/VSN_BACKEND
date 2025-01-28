package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.exception.FriendException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.FriendSummaryDTO;
import com.generation.vsnbackend.model.dto.ProfileDTOResp;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.entities.Friend;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.User;
import com.generation.vsnbackend.model.entities.signin.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    CredentialService credentialService;
    @Autowired
    DTOConverter dtoConverter;
    @Autowired
    DTOSteamConverter dtoSteamConverter;
    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    ControllerHelper ch;

    /**
     * Retrieves a list of friends (followings) associated with the currently logged-in user.
     *
     * This method identifies the logged-in user by extracting their credentials using the
     * {@link CredentialService#getUserByToken()} method. It then fetches the list of followers
     * (users they are following) from the user's profile and converts each friend entity into
     * a {@link FriendSummaryDTO} object using the {@link com.generation.vsnbackend.model.dto.DTOConverter#toFriendSummaryDTO(Friend)} method.
     *
     * @return a {@link List} of {@link FriendSummaryDTO} objects representing the friends the user is following.
     *         Returns an empty list if the user has no followings.
     * @throws IllegalStateException if the user or their profile is null.
     */
    @GetMapping("/following")
    public List<FriendSummaryDTO> getAllFriends(){
        //quelli che seguo
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> followings=new ArrayList<>();
        for(Friend f:user.getProfile().getFollowers())
            followings.add(dtoConverter.toFriendSummaryDTO(f));
        return followings;
    }

    /**
     * Retrieves a list of followers associated with the currently logged-in user.
     *
     * This method retrieves the currently authenticated user using the
     * {@link CredentialService#getUserByToken()} method. It accesses the user's profile
     * and fetches the list of followers (users who follow the authenticated user) from
     * the `followings` field. Each follower entity is then converted into a
     * {@link FriendSummaryDTO} object using the {@link  com.generation.vsnbackend.model.dto.DTOConverter#toFriendSummaryDTO(Friend)} method.
     *
     * @return a {@link List} of {@link FriendSummaryDTO} objects representing the followers of the user.
     *         Returns an empty list if the user has no followers.
     * @throws IllegalStateException if the user or their profile is null.
     */
    @GetMapping("/follower")
    public List<FriendSummaryDTO> getAllFollowers(){
        //quelli che mi seguono
        User user=credentialService.getUserByToken();
        List<FriendSummaryDTO> followers=new ArrayList<>();
        for(Friend f:user.getProfile().getFollowings())
            followers.add(dtoConverter.toFriendSummaryDTOxFollower(f));
        return followers;
    }


    /**
     * Retrieves a list of followers for the user associated with the specified profile ID.
     *
     * This method allows fetching the followers of a user based on their profile ID. It uses
     * the { ProfileService#getOneById(Long)} method to retrieve the profile corresponding
     * to the given ID. Once the profile is obtained, the followers (users who follow the profile's owner)
     * are accessed from the `followers` field of the profile. Each follower is then converted into a
     * {@link FriendSummaryDTO} object using the {@link  com.generation.vsnbackend.model.dto.DTOConverter#toFriendSummaryDTO(Friend)} method.
     *
     * @param id the unique identifier of the profile whose followers are to be retrieved.
     * @return a {@link List} of {@link FriendSummaryDTO} objects representing the followers of the user
     *         associated with the specified profile ID.
     *         Returns an empty list if the profile has no followers.
     * @throws EntityNotFoundException if no profile is found for the specified ID.
     * @throws IllegalStateException if the profile or its followers are null.
     */
    @GetMapping("/followers/{id}")
    public List<FriendSummaryDTO> getAllFriends(@PathVariable Long id){

        //per avere i followers di chi sta nell'id (diverso da me)
        Profile profile = ch.profileService.getOneById(id);

        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f : profile.getFollowers())
        {
            friends.add(dtoConverter.toFriendSummaryDTO(f));
        }
        return friends;
    }

    /**
     * Retrieves a list of users that the profile associated with the specified ID is following.
     *
     * This method fetches the users (followings) that the owner of the profile, identified by the given ID,
     * is currently following. It uses the {ProfileService#getOneById(Long)} method to retrieve the
     * profile associated with the provided ID. Once the profile is retrieved, the list of followings
     * is accessed via the `followings` field of the profile. Each following is then converted into a
     * {@link FriendSummaryDTO} object using the {@link  com.generation.vsnbackend.model.dto.DTOConverter#toFriendSummaryDTO(Friend)} method.
     *
     * @param id the unique identifier of the profile whose followings are to be retrieved.
     * @return a {@link List} of {@link FriendSummaryDTO} objects representing the users that the profile's owner
     *         is following. Returns an empty list if the profile has no followings.
     * @throws EntityNotFoundException if no profile is found for the specified ID.
     * @throws IllegalStateException if the profile or its followings are null.
     */
    @GetMapping("/followings/{id}")
    public List<FriendSummaryDTO> getAllFriendsBis(@PathVariable Long id){

        //per avere i following di chi sta nell'id (diverso da me)
        Profile profile = ch.profileService.getOneById(id);

        List<FriendSummaryDTO> friends=new ArrayList<>();
        for(Friend f : profile.getFollowings())
        {
            friends.add(dtoConverter.toFriendSummaryDTOxFollower(f));
        }
        return friends;
    }

    /**
     * Retrieves detailed information about a friend's profile using their profile ID.
     *
     * This method fetches the profile associated with the provided friendId. It integrates
     * data from the Steam API to update the friend's profile with information about their
     * last played game and their Steam name. If an error occurs during the API interaction,
     * the profile is returned without the Steam-related updates.
     *
     * The method performs the following steps:
     * - Fetches the profile using the provided ID.
     * - Calls the Steam API to retrieve the friend's last played game details and Steam name.
     * - Updates the profile with the retrieved information, including the last played game's ID,
     *   image URL, and name.
     * - Saves the updated profile to the database.
     * - Converts the profile into a ProfileDTOResp object and returns it.
     *
     * If an exception occurs during the Steam API interaction, the profile is returned as is,
     * without the Steam-related updates.
     *
     * @param friendId The unique identifier of the friend's profile.
     * @return A ProfileDTOResp containing the friend's profile information.
     * @throws EntityNotFoundException If no profile is found for the provided friendId.
     * @throws IllegalStateException If the profile or its associated user is null.
     */
    @GetMapping("/following/{friendId}")
    public ProfileDTOResp getDetailFriend(@PathVariable Long friendId){

        //arriva id del profilo dell'amico di chi sta usando il sito
        Profile profile=ch.profileService.getOneById(friendId);

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(steamAPIService.getLastPlayedGame(profile.getUser().getSteamId()));
            JsonNode lastVideogame = rootNode.path("response").path("games").get(0);
            profile.setLastPlayedVideogameAppId(lastVideogame.path("appid").asLong());
            profile.setLastPlayedGameImgUrl(steamAPIService.getUrlImageVideogame(profile.getLastPlayedVideogameAppId(), lastVideogame.path("img_icon_url").asText()));
            profile.setLastPlayedGameName(lastVideogame.path("name").asText());
            ch.profileService.save(profile);

            JsonNode steamName = objectMapper.readTree(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
            profile.setSteamName(steamName.path("response").path("players").get(0).path("personaname").asText());
            profile.setProfileName(profile.getUser().getUsername());

            ch.profileService.save(profile);
            return dtoConverter.toProfileDtoResp(profile);
        }
        catch(Exception e)
        {
            return dtoConverter.toProfileDtoResp(profile);
        }
    }

    /**
     * Adds a new friend connection between the currently authenticated user and another user.
     *
     * This method allows the authenticated user to follow another user's profile by creating
     * a new friendship link. The following steps are performed:
     * - Verifies that the user is not attempting to follow their own profile.
     * - Fetches the profile of the user to be followed using the provided `friendProfileId`.
     * - Creates a new `Friend` entity to represent the relationship.
     * - Updates the follower and following lists for both profiles.
     * - Saves all updated entities to the database.
     *
     * If the user attempts to follow their own profile, a `FriendException` is thrown.
     *
     * @param friendProfileId The ID of the profile to be followed.
     * @return A `FriendSummaryDTO` containing details about the new friendship link.
     * @throws FriendException If the user attempts to follow their own profile.
     * @throws EntityNotFoundException If the profile with the given `friendProfileId` does not exist.
     */
    @PostMapping("/add/{friendProfileId}")
    public FriendSummaryDTO addOneFriend(@PathVariable Long friendProfileId){
        User userRequesting=credentialService.getUserByToken();

        if(Objects.equals(friendProfileId, userRequesting.getProfile().getId())) {
            throw new FriendException("Non puoi diventare amico di te stesso, coglione");
        }
        Profile profileReceiving =ch.profileService.getOneById(friendProfileId);

        Friend friend = new Friend();

        friend.setProfile_follower(userRequesting.getProfile());
        friend.setProfile_following(profileReceiving);
        ch.friendService.save(friend);

        profileReceiving.getFollowers().add(friend);
        userRequesting.getProfile().getFollowings().add(friend);

        ch.profileService.save(profileReceiving);
        ch.profileService.save(userRequesting.getProfile());
        ch.userService.save(userRequesting);
        ch.userService.save(profileReceiving.getUser());


        return dtoConverter.toFriendSummaryDTO(friend);
    }

    /**
     * Endpoint to unfollow a profile identified by the provided ID.
     *
     * @param idProfileToUnfollow The ID of the profile to unfollow.
     * @return FriendSummaryDTO representing the unfollowed friend.
     * @throws FriendException if the friend to unfollow is not found.
     */
    @DeleteMapping("/followings/{idProfileToUnfollow}")
    public FriendSummaryDTO unfollow(@PathVariable Long idProfileToUnfollow){
        User userRequesting=credentialService.getUserByToken();
        Long idProfileRequesting=userRequesting.getProfile().getId();
        Friend friendToUnfollow=ch.getOneFriendByFollowingIdAndFollowerId(idProfileToUnfollow,idProfileRequesting);

        if(friendToUnfollow==null)
            throw new FriendException("Amico non presente, coglione!");

        Long friendId=friendToUnfollow.getId();

        Profile profileReceiving =ch.profileService.getOneById(idProfileToUnfollow);
        Friend friend = ch.friendService.getOneById(friendId);

        userRequesting.getProfile().getFollowers().remove(friend);
        profileReceiving.getFollowings().remove(friend);

        ch.profileService.save(profileReceiving);
        ch.profileService.save(userRequesting.getProfile());
        ch.userService.save(userRequesting);
        ch.userService.save(profileReceiving.getUser());

        ch.friendService.deleteById(friendId);
        return dtoConverter.toFriendSummaryDTO(friend);
    }
}
