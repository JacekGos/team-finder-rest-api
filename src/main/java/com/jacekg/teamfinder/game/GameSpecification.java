package com.jacekg.teamfinder.game;

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
	
//	public Specification<Game> getUsers(Map<String, String> filterParams) {
//
//	        return (root, query, criteriaBuilder) -> {
//	        	
//	        	
//	        	
//	            List<Predicate> predicates = new ArrayList<>();
//	            if (filterParams.get("address") != null && !filterParams.get("address").isEmpty()) {
//	            	
//	                predicates.add(criteriaBuilder.like(root.get("address"), filterParams.get("address")));
//	            }
////	            if (request.getName() != null && !request.getName().isEmpty()) {
////	                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
////	                        "%" + request.getName().toLowerCase() + "%"));
////	            }
////	            if (request.getGender() != null && !request.getGender().isEmpty()) {
////	                predicates.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
////	            }
//	            
//	            query.orderBy(criteriaBuilder.desc(root.get("id")));
//	            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//	        };
//	    }
	
	private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

	public Specification<Game> getUsers(Map<String, String> filterParams) {

		return new Specification<Game>() {

			List<Predicate> predicates = new ArrayList<>();
			
			

			@Override
			public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				logger.info("address: " + filterParams.get("address"));

				root.fetch("venue", JoinType.LEFT);

				if (!filterParams.get("address").equals(null)) {
					
					
					predicates.add(criteriaBuilder.like(
							root.get("venue").get("address"), 
							"%" + filterParams.get("address") + "%"));
					
					logger.info("address is not null");
				}

				query.orderBy(criteriaBuilder.desc(root.get("id")));

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};

	}
}
