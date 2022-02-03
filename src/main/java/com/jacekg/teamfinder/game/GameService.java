package com.jacekg.teamfinder.game;

import java.security.Principal;

public interface GameService {
	
	public GameResponse save(GameRequest gameRequest, Principal principal);
}
