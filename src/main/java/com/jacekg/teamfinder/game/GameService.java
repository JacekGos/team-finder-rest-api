package com.jacekg.teamfinder.game;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface GameService {
	
	public GameResponse save(GameRequest gameRequest, Principal principal);

	public List<GameResponse> getAll();

	public List<GameResponse> getAllByFilters(Map<String, String> filterParams);
}
