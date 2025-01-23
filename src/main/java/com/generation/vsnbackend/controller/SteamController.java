package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dto.PlayerDTO;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/steams")
public class SteamController {
    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    DTOConverter dtoConverter;

    @Autowired
    CredentialService credentialService;

    @GetMapping
    public PlayerDTO getPlayerDto() throws JsonProcessingException {
        Profile profile=credentialService.getUserByToken().getProfile();
        return dtoConverter.toPlayerDTO(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
    }
}
