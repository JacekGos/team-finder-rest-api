package com.jacekg.teamfinder.game;

import java.security.Principal;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		return null;
	}

}
