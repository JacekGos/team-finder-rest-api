package com.jacekg.teamfinder.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface GameRepository extends JpaRepository<Game, Long>, QueryByExampleExecutor<Game> {

}
