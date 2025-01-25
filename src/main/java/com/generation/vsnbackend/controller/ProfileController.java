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

    @GetMapping("/all")
    List<ProfileDTOResp> getAllProfiles() {
        List<ProfileDTOResp> allProfiles = new ArrayList<>();
        for(Profile profile: ch.profileService.getList())
            allProfiles.add(dtoConverter.toProfileDtoResp(profile));
        return allProfiles;
    }

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

    @GetMapping("/fileSystem/{id}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id) throws IOException {
        byte[] imageData=fileDataService.downloadImageFromFileSystem(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("image/png"))
                .body(imageData);
    }

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
