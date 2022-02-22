package com.jacekg.teamfinder.game;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
	@NotBlank(message = "required")
	@NotBlank(message = "name cannot be empty")
	@Size(max = 50, message = "too long name")
	private String name;
	
	@NotNull(message = "required")
	@NotBlank(message = "discipline cannot be empty")
	private String sportDisciplineName;
	
	@Min(value = 2, message = "at least two players")
	@Max(value = 30, message = "max 30 players")
	private int amountOfPlayers;
	
	@NotNull(message = "required")
	@ValidDuration
	private int duration;
	
	@NotNull(message = "required")
	@Min(value = 0, message = "min value 0")
	@Max(value = 10000, message = "max value 10000")
	private int price;
	
	@NotNull(message = "required")
	@Min(value = 0, message = "min value 0")
	private long venueId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "required")
	private LocalDate date;
	
	@Min(value = 5, message = "must be in range 5-22")
	@Max(value = 22, message = "must be in range 5-22")
	private int hour;

	private String description;

	public void setName(String name) {
		if (name != null) {
			this.name = name.trim();
		}
	}

	public void setSportDisciplineName(String sportDisciplineName) {
		if (sportDisciplineName != null) {
			this.sportDisciplineName = sportDisciplineName.trim();
		}
	}

	public void setDescription(String description) {
		if (description != null) {
			this.description = description.trim();
		}
	}
}
