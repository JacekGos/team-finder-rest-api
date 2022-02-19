package com.jacekg.teamfinder.game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jacekg.teamfinder.sport_discipline.SportDiscipline;
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
	private LocalDateTime date;
	
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
	private User creator;

	@ManyToMany(mappedBy = "participatedGames")
	private Set<User> players = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "sport_discipline_id")
	private SportDiscipline sportDiscipline;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "venue_id")
	private Venue venue;
	
	public void addCreator(User user) {
		
		this.creator = user;
		user.addCreatedGame(this);
	}
	
	public void addSportDiscipline(SportDiscipline sportDiscipline) {
		
		this.sportDiscipline = sportDiscipline;
		sportDiscipline.addGame(this);
	}
}











