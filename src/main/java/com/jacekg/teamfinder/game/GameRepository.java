package com.jacekg.teamfinder.game;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
	
	public List<Game> findAll(Specification<Game> spec);
}
