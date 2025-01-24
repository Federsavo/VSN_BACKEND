package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dtoSteam.DTOSteamConverter;
import com.generation.vsnbackend.model.dtoSteam.VideogameDetailDTO;
import com.generation.vsnbackend.model.entities.Videogame;
import com.generation.vsnbackend.model.entities.signin.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping
    public Response updatePreferredVideogame(@RequestBody Videogame videogame)
    {
        ch.videogameService.save(videogame);
        return new Response("Successfully updated videogame");
    }
}
