package com.generation.vsnbackend.controller;

import com.generation.vsnbackend.controller.helper.ControllerHelper;
import com.generation.vsnbackend.model.dtoSteam.SingleOwnedGameDTO;
import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.Videogame;
import com.generation.vsnbackend.model.repositories.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class VideogameHelper
{
	@Autowired
	ControllerHelper ch;
	@Autowired
	VideogameRepository videogameRepo;

	public void fillVideogameDb(Set<Long> appIds, List<SingleOwnedGameDTO> games, Profile profile)
	{
		List<Videogame> newGames = new ArrayList<>();
		for (SingleOwnedGameDTO game : games)
		{
			Long appId=game.getAppId();
			if(!appIds.contains(appId))
			{
				Videogame v = new Videogame();
				v.setPreferred(false);
				v.setNumberOfStars(0);
				v.setIconImgUrl(game.getIconImgUrl());
				v.setAppId(appId);
				v.setNameVideogame(game.getVideogameName());
				v.setProfile(profile);
				profile.getVideogames().add(v);
				newGames.add(v);
			}
		}
		videogameRepo.saveAll(newGames);
	}

}
