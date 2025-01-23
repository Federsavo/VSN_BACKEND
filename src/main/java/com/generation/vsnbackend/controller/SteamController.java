package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.controller.helper.FileDataService;
import com.generation.vsnbackend.model.dto.DTOConverter;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.NewsDTO;
import com.generation.vsnbackend.model.dtoSteam.PlayerDTO;
import com.generation.vsnbackend.model.dtoSteam.SingleGameAchievementsDTO;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.images.FileData;
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/steams")
public class SteamController {
    @Autowired
    SteamAPIService steamAPIService;

    @Autowired
    DTOSteamConverter dtoSteamConverter;

    @Autowired
    CredentialService credentialService;

    @Autowired
    private FileDataService fileDataService;
    @Autowired
    ControllerHelper ch;

    @GetMapping("/player")
    public PlayerDTO getPlayerDto() throws JsonProcessingException {
        Profile profile=credentialService.getUserByToken().getProfile();
        return dtoSteamConverter.toPlayerDTO(steamAPIService.getPlayerSummary(profile.getUser().getSteamId()));
    }

    @GetMapping("/news/{videogameId}")
    public List<NewsDTO> getNewsVideogame(@PathVariable Long videogameId) throws JsonProcessingException {
        String json= steamAPIService.getVideogameNews(videogameId);

        return dtoSteamConverter.toNewsDTOs(json);
    }

}
