package com.jacekg.teamfinder.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;

import com.jacekg.teamfinder.user.UserResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor
public class GameRestController {
	
	@PostMapping("/games")
	public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameRequest gameRequest) {
		
		return status(HttpStatus.CREATED).body(gameService.save(gameRequest));
	}
}




