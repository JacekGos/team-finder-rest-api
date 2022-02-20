package com.jacekg.teamfinder.game;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameResponse {
	
	private String name;
	
	private String sportDisciplineName;
	
	private int amountOfPlayers;
	
	private int duration;
	
	private int price;
	
	private String venueName;
	
	private String venueAddress;
	
	private LocalDateTime date;
	
	private String description;
}
