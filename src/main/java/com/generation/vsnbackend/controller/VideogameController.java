package com.generation.vsnbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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





    @GetMapping("/{appId}")
    public VideogameDetailDTO getVideogameDetail(@PathVariable("appId") Long appId) throws JsonProcessingException {
        String videogameDetailJson = steamAPIService.getOneVideogameDetail(appId);
        Videogame videogame = ch.findOneVideogameByAppId(appId);
        System.out.println(videogame.getGenre());
        videogame=dtoSteamConverter.toVideogameFromSteam(steamAPIService.getOneVideogameDetail(appId),videogame);
        ch.videogameService.save(videogame);

        return dtoSteamConverter.toVideogameDetailFromSteam(videogame,steamAPIService.getOneVideogameDetail(videogame.getAppId()));

    }


    @PutMapping
    public Response updateVideogame(@RequestBody Videogame videogame)
    {
        ch.videogameService.save(videogame);
        return new Response("Successfully updated videogame");
    }
}
