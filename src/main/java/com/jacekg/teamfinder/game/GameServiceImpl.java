package com.jacekg.teamfinder.game;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	private ModelMapper modelMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	@Override
	public GameResponse save(GameRequest gameRequest, Principal principal) {
		
//		return modelMapper.map(gameRepository.save(game), GameResponse.class);
		logger.info("gameRequest: " + gameRequest);
		return new GameResponse();
	}
	
}
