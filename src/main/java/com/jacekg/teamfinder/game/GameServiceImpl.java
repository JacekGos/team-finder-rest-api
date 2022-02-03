package com.jacekg.teamfinder.game;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	private ModelMapper modelMapper;
	
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
//		return modelMapper.map(gameRepository.save(game), GameResponse.class);
		return new GameResponse();
	}

}
