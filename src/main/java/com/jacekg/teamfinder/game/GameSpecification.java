package com.jacekg.teamfinder.game;

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

@Component
public class GameSpecification {
		
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

	public Specification<Game> getUsers(Map<String, String> filterParams) {

		return new Specification<Game>() {

			List<Predicate> predicates = new ArrayList<>();
			
			@Override
			public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				root.fetch("venue", JoinType.LEFT);
				root.fetch("sportDiscipline", JoinType.LEFT);
				
				if (!filterParams.get("address").equals(null) && !filterParams.get("address").isEmpty()) {
					
					predicates.add(criteriaBuilder.like(
							root.get("venue").get("address"), 
							"%" + filterParams.get("address") + "%"));
					
				} if (!filterParams.get("sportDiscipline").equals(null) && !filterParams.get("sportDiscipline").isEmpty()) {
					
					predicates.add(criteriaBuilder.like(
							root.get("sportDiscipline").get("name"), 
							filterParams.get("sportDiscipline")));
					
				} if (!filterParams.get("priceMin").equals(null) && !filterParams.get("priceMin").isEmpty()
						&& !filterParams.get("priceMax").equals(null) && !filterParams.get("priceMax").isEmpty()) {
					
					Integer priceMin = Integer.parseInt(filterParams.get("priceMin"));
					Integer priceMax = Integer.parseInt(filterParams.get("priceMax"));
					
					predicates.add(criteriaBuilder.between(root.get("price"), priceMin, priceMax));
					
				} if (!filterParams.get("startDate").equals(null) && !filterParams.get("startDate").isEmpty()
						&& !filterParams.get("endDate").equals(null) && !filterParams.get("endDate").isEmpty()) {
					
					DateTimeFormatter dateTimeFormatter 
						= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					 
					LocalDateTime startDate = LocalDateTime.parse(filterParams.get("startDate") + "T00:00");
					LocalDateTime endDate = LocalDateTime.parse(filterParams.get("endDate") + "T00:00");
					
					logger.info("startdate: " + startDate);
					logger.info("enddate: " + endDate);
					
					predicates.add(criteriaBuilder.between(root.get("date"), startDate, endDate));
				}
				

				query.orderBy(criteriaBuilder.desc(root.get("id")));

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};

	}
}
