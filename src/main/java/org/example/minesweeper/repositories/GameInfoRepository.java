package org.example.minesweeper.repositories;

import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GameInfoRepository extends JpaRepository<GameInfoResponse, String> {

}
