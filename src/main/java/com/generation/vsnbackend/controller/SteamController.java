package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.PlayerDTO;
import com.generation.vsnbackend.model.dtoSteam.SingleGameAchievementsDTO;
import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/steams")
public class SteamController {
    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    DTOSteamConverter dtoSteamConverter;

    @Autowired
    CredentialService credentialService;

    @GetMapping
    public PlayerDTO getPlayerDto() throws JsonProcessingException {
        Profile profile=credentialService.getUserByToken().getProfile();
        return dtoSteamConverter.toPlayerDTO(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
    }
}
