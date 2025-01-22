package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.controller.helper.FileDataService;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.ProfileDTOResp;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.images.FileData;
import com.generation.vsnbackend.model.entities.signin.SignIn;
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
    SignIn saveBackdropImage(
            @RequestParam("imgBackdrop") MultipartFile imgBackdrop

    ) throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();
        FileData img1=fileDataService.uploadImageToFileSystem(imgBackdrop);
        if(img1!=null)
            profile.setProfileBackdropImgId(img1.getId());
        ch.profileService.save(profile);
        return new SignIn("Backdrop image saved successfully");
    }

    @PostMapping("/saveProfileImage")
    SignIn saveProfileImage(
            @RequestParam("imgProfile") MultipartFile imgProfile
    ) throws IOException
	{
        Profile profile=credentialService.getUserByToken().getProfile();
        FileData img1=fileDataService.uploadImageToFileSystem(imgProfile);
        if(img1!=null)
            profile.setProfileBackdropImgId(img1.getId());
        ch.profileService.save(profile);
        return new SignIn("Profile image saved successfully");
    }
}
