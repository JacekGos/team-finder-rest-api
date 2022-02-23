package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.util.List;

public interface GameService {
	
	public GameResponse save(GameRequest gameRequest, Principal principal);

	public List<GameResponse> getAllGames();
}
