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
     * Retrieves the profile of the authenticated user. The user's profile is obtained
     * using the token from the request, and it includes information about the last played
     * video game retrieved from an external API.
     *
     * @return a ProfileDTOResp object representing the user's profile
     * @throws IOException if there is an error while reading data from the external API
     */
    @GetMapping
    ProfileDTOResp getProfile() throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(steamAPIService.getLastPlayedGame(profile.getUser().getSteamId()));
        JsonNode lastVideogame = rootNode.path("response").path("games").get(0);
        profile.setLastPlayedVideogameAppId(lastVideogame.path("appid").asLong());
        profile.setLastPlayedGameImgUrl(steamAPIService.getUrlImageVideogame(profile.getLastPlayedVideogameAppId(),lastVideogame.path("img_icon_url").asText()));
        profile.setLastPlayedGameName(lastVideogame.path("name").asText());
        return dtoConverter.toProfileDtoResp(profile);
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
    public Response saveBackdropImage(@RequestParam("imgBackdrop") MultipartFile imgBackdrop) throws IOException
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
     * Saves a profile image for the authenticated user's profile. The user's profile is retrieved
     * using the token from the request. The image is uploaded to the file system, and the profile
     * is updated with the new profile image ID.
     *
     * @param imgProfile the profile image file to be saved
     * @return a Response indicating the result of the save operation
     * @throws IOException if there is an error during the image upload
     */
    @PostMapping("/saveProfileImage")
    public Response saveProfileImage( @RequestParam("imgProfile") MultipartFile imgProfile) throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();
        Long id=profile.getId();
        FileData img=fileDataService.uploadImageToFileSystem(imgProfile,id);
        if(img!=null)
            profile.setProfileBackdropImgId(img.getId());
        ch.profileService.save(profile);
        return new Response("Profile image saved successfully");
    }
}
