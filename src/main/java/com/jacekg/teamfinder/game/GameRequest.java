package com.jacekg.teamfinder.game;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.jacekg.teamfinder.validation.ValidDuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameRequest {
	
	@NotNull(message = "required")
	@Size(min = 1, max = 50, message = "too long name")
	private String name;
	
	@NotNull(message = "required")
	private String sportDisciplineName;
	
	@Min(value = 2, message = "at least two players")
	@Max(value = 30, message = "max 30 players")
	private int amountOfPlayers;
	
	@ValidDuration
	private int duration;
	
	private int price;
	
	private long venueId;
	
	private LocalDate date;
	
	private int hour;
}
