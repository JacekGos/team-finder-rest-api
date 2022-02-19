package com.jacekg.teamfinder.sport_discipline;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jacekg.teamfinder.game.Game;

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
@Table(name = "sport_discipline")
public class SportDiscipline {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "sportDiscipline",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Game> games;
	
	public void addGame(Game game) {
		
		this.games.add(game);
	}
}
