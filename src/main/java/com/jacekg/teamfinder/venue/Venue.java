package com.jacekg.teamfinder.venue;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jacekg.teamfinder.discipline.SportDiscipline;

public class Venue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	//many to one
	private SportDiscipline sportDiscipline;
	
	//variable or list for store busy terms of venue
}
