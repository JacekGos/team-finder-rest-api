package com.jacekg.teamfinder.game;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jacekg.teamfinder.venue.VenueRepository;
import com.jacekg.teamfinder.venue.VenueService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GameSpecification {
	
	private VenueService venueService;
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
	
	public Specification<Game> getUsers(Map<String, String> filterParams) {

		return new Specification<Game>() {

			List<Predicate> predicates = new ArrayList<>();
			
			@Override
			public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				root.fetch("venue", JoinType.LEFT);
				root.fetch("sportDiscipline", JoinType.LEFT);
				
				if (!filterParams.get("address").equals(null) && !filterParams.get("address").isEmpty()
						&& !filterParams.get("sportDiscipline").equals(null) && !filterParams.get("sportDiscipline").isEmpty()) {
					
					List<Long> venuesId = null;
					
					try {
						venuesId = venueService.getAllIdsBySportDysciplineAndAddress(
								filterParams.get("sportDiscipline"),
								filterParams.get("address"), 
								Double.parseDouble(filterParams.get("range")));
						
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					predicates.add(criteriaBuilder.in(root.get("venue").get("id")).value(venuesId));
					
				} if (!filterParams.get("sportDiscipline").equals(null) && !filterParams.get("sportDiscipline").isEmpty()) {
					
					predicates.add(criteriaBuilder.like(
							root.get("sportDiscipline").get("name"), 
							filterParams.get("sportDiscipline")));
					
				} if (!filterParams.get("priceMin").equals(null) && !filterParams.get("priceMin").isEmpty()
						&& !filterParams.get("priceMax").equals(null) && !filterParams.get("priceMax").isEmpty()) {
					
					int priceMin = Integer.parseInt(filterParams.get("priceMin"));
					int priceMax = Integer.parseInt(filterParams.get("priceMax"));
					
					predicates.add(criteriaBuilder.between(root.get("price"), priceMin, priceMax));
					
				} if (!filterParams.get("startDate").equals(null) && !filterParams.get("startDate").isEmpty()
						&& !filterParams.get("endDate").equals(null) && !filterParams.get("endDate").isEmpty()) {
					
					LocalDateTime startDate = LocalDateTime.parse(filterParams.get("startDate") + "T00:00");
					LocalDateTime endDate = LocalDateTime.parse(filterParams.get("endDate") + "T00:00");
					
					predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate.plusDays(1)));
					
				} if (!filterParams.get("playersMin").equals(null) && !filterParams.get("playersMin").isEmpty()
						&& !filterParams.get("playersMax").equals(null) && !filterParams.get("playersMax").isEmpty()) {
					
					int playersMin = Integer.parseInt(filterParams.get("playersMin"));
					int playersMax = Integer.parseInt(filterParams.get("playersMax"));
					
					predicates.add(criteriaBuilder.between(root.get("amountOfPlayers"), playersMin, playersMax));
				
				} if (!filterParams.get("duration").equals(null) && !filterParams.get("duration").isEmpty()) {
					
					int duration = Integer.parseInt(filterParams.get("duration"));
					
					predicates.add(criteriaBuilder.equal(root.get("duration"), duration));
				
				}
				
				query.orderBy(criteriaBuilder.desc(root.get("id")));

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};

	}
}
