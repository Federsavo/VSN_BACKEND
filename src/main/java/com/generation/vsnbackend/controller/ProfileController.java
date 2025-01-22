package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.ProfileDTOReq;
import com.generation.vsnbackend.model.dto.ProfileDTOResp;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    ControllerHelper ch;

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
    ProfileDTOResp getProfile() {
        Profile profile=credentialService.getUserByToken().getProfile();
        return dtoConverter.toProfileDtoResp(profile);
    }

    @PostMapping
    ProfileDTOResp addProfile(@RequestBody ProfileDTOReq profileDTOReq) {
        Profile profile=credentialService.getUserByToken().getProfile();
        ch.profileService.save(profile);
        return dtoConverter.toProfileDtoResp(profile);
    }
}
