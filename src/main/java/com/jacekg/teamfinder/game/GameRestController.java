package com.jacekg.teamfinder.game;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/game/")
@AllArgsConstructor
public class GameRestController {
	
	@PostMapping()
}
