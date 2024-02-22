package org.example.minesweeper.repositories;

import org.example.minesweeper.createTable.GameInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepository extends JpaRepository<GameInfoResponse, Long> {

}
