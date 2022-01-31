package com.jacekg.teamfinder.game;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jacekg.teamfinder.discipline.SportDiscipline;
import com.jacekg.teamfinder.user.User;
import com.jacekg.teamfinder.venue.Venue;

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
@Entity
@Table(name = "game")
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "duration", nullable = false)
	private int duration;
	
	@Column(name = "amount_of_players", nullable = false)
	private int amountOfPlayers;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "user_id")
	private User organizer;
	
	//many to one
//	@Column(name = "organizer_id", nullable = false)
	private SportDiscipline sportDiscipline;
	
	//many to many
	private List<User> players;
	
	//many to one
	private Venue venue;
	
	
}











