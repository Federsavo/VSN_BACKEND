package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.VideogameDetailDTO;
import com.generation.vsnbackend.model.entities.Videogame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/videogames")
public class VideogameController {

    @Autowired
    SteamAPIService steamAPIService;
    @Autowired
    DTOSteamConverter dtoSteamConverter;
    @Autowired
    CredentialService credentialService;
    @Autowired
    ControllerHelper ch;


    @GetMapping("/{id}")
    public VideogameDetailDTO getVideogameDetail(@PathVariable("id") Long id) {
        Videogame videogame = new Videogame();
                return null;
    }
}
