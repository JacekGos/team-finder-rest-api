package com.jacekg.teamfinder.venue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jacekg.teamfinder.sport_discipline.SportDiscipline;

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
@Table(name = "venue")
public class Venue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(columnDefinition = "geometry")
	private Point location;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "venue_type_id")
	private VenueType venueType;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "venue_id")
	private List<Term> busyTerms = new ArrayList<>();
	
	public List<LocalDateTime> mapBusyTermsToDates() {
		
		List<LocalDateTime> busyTermsDates = new ArrayList<LocalDateTime>();
		
		this.busyTerms.forEach(term -> busyTermsDates.add(term.getBusyTerm()));
		
		return busyTermsDates;
	}
	
	public void addTerms(List<Term> gameTerms) {
		
		gameTerms.stream()
			.forEach(term -> this.busyTerms.add(term));
		
	}
}
