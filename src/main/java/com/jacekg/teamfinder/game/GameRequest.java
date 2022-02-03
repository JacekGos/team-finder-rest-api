package com.jacekg.teamfinder.game;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@NotNull(message = "required")
	@ValidDuration
	private int duration;
	
	@NotNull(message = "required")
	@Min(value = 0)
	@Max(value = 10000, message = "max 10000")
	private int price;
	
	@NotNull(message = "required")
	private long venueId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "required")
	private LocalDate date;
	
	@Min(value = 0, message = "not allowed")
	@Max(value = 23, message = "not allowed")
	private int hour;
}
