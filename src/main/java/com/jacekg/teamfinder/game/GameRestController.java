package com.jacekg.teamfinder.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor
public class GameRestController {

	private GameService gameService;
	
	private static final Logger logger = LoggerFactory.getLogger(GameRestController.class);
	
	@PostMapping("/games")
	public ResponseEntity<GameResponse> createGame
		(@Valid @RequestBody GameRequest gameRequest, Principal principal) {
		
		return status(HttpStatus.CREATED).body(gameService.save(gameRequest, principal));
	}
	
	@GetMapping("/games")
	public ResponseEntity<List<GameResponse>> getAll() {
		
		return status(HttpStatus.OK).body(gameService.getAll());
	}
	
	@GetMapping("/games/filter")
	public ResponseEntity<List<GameResponse>> getAllByFilters
		(@RequestParam Map<String, String> filterParams) throws IOException {
		
		return status(HttpStatus.OK)
				.body(gameService.getAllByFilters(filterParams));
	}
}



