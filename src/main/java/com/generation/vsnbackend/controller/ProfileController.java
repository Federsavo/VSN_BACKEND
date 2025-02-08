package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.controller.helper.FileDataService;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.ProfileDTOResp;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.images.FileData;
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    ControllerHelper ch;
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    DTOConverter dtoConverter;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    SteamAPIService steamAPIService;

    /**
     * Retrieves a list of all user profiles in the system. This method does not require
     * any authentication since it returns public profile data. Each profile is converted
     * to a ProfileDTOResp object before being returned.
     *
     * @return a List of ProfileDTOResp objects representing all user profiles
     */
    @GetMapping("/all")
    List<ProfileDTOResp> getAllProfiles() {
        List<ProfileDTOResp> allProfiles = new ArrayList<>();
        for(Profile profile: ch.profileService.getList())
            allProfiles.add(dtoConverter.toProfileDtoResp(profile));
        return allProfiles;
    }

    /**
     * Retrieves the profile of the currently authenticated user along with details about their last played game.
     *
     * This method fetches the user's profile based on their token, and attempts to retrieve information
     * about their last played game from the Steam API. If the last played game details are successfully
     * fetched, they are set in the user's profile. If an exception occurs during this process, the profile
     * is returned without last played game details.
     *
     * @return A {@link ProfileDTOResp} object containing the user's profile information.
	 */
    @GetMapping
    ProfileDTOResp getProfile()
	{
        Profile profile=credentialService.getUserByToken().getProfile();
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
     * Retrieves a profile by its ID.
     *
     * This method fetches a Profile entity corresponding to the given ID and
     * converts it into a ProfileDTOResp object to be returned to the client.
     * If the profile is not found, an exception will be thrown.
     *
     * @param id The ID of the profile to retrieve.
     * @return A ProfileDTOResp object representing the retrieved profile.
     * @throws Exception If the profile with the specified ID is not found
     *                   or if an error occurs during the retrieval process.
     */
    @GetMapping("/{id}")
    ProfileDTOResp getProfileById(@PathVariable long id) throws Exception
    {
        Profile profileDTO = ch.profileService.getOneById(id);
        return dtoConverter.toProfileDtoResp(profileDTO);
    }

    /**
     * Downloads an image from the file system by its ID.
     * The image is returned as a byte array in the response body with a content type of "image/png".
     *
     * @param id the ID of the image to be downloaded
     * @return a ResponseEntity containing the image data
     * @throws IOException if there is an error during the image retrieval from the file system
     */
    @GetMapping("/fileSystem/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id) throws IOException {
        byte[] imageData=fileDataService.downloadImageFromFileSystem(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("image/png"))
                .body(imageData);
    }

    /**
     * Saves a backdrop image for the authenticated user's profile. The user's profile is retrieved
     * using the token from the request. The image is uploaded to the file system, and the profile
     * is updated with the new backdrop image ID.
     *
     * @param imgBackdrop the backdrop image file to be saved
     * @return a Response indicating the result of the save operation
     * @throws IOException if there is an error during the image upload
     */
    @PostMapping("/saveBackdropImage")
    Response saveBackdropImage(
            @RequestParam("imgBackdrop") MultipartFile imgBackdrop
    ) throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();
        Long id=profile.getId();
        FileData img=fileDataService.uploadImageToFileSystem(imgBackdrop,id);
        if(img!=null)
            profile.setProfileBackdropImgId(img.getId());
        ch.profileService.save(profile);
        return new Response("Backdrop image saved successfully");
    }

    /**
     * Saves the profile image for the currently authenticated user.
     *
     * This method allows users to upload a profile image, which is stored in the file system.
     * The uploaded image is associated with the user's profile by saving the image's ID.
     *
     *
     * @param imgProfile The {@link MultipartFile} representing the uploaded profile image.
     *                   This parameter must be provided in the HTTP request as "imgProfile".
     * @return A {@link Response} object containing a success message indicating that the profile image was saved successfully.
     * @throws IOException If an error occurs during file upload or storage.
     */
    @PostMapping("/saveProfileImage")
    Response saveProfileImage(
            @RequestParam("imgProfile") MultipartFile imgProfile
    ) throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();
        Long id=profile.getId();
        FileData img=fileDataService.uploadImageToFileSystem(imgProfile,id);
        if(img!=null)
            profile.setProfileImgId(img.getId());
        ch.profileService.save(profile);
        return new Response("Profile image saved successfully");
    }
}
