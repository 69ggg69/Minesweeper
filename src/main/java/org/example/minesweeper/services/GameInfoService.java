package org.example.minesweeper.services;

import org.example.minesweeper.dto.GameInfoData;

public interface GameInfoService {
    void saveGameInfo(GameInfoData gameInfoData);
    GameInfoData getGameInfo(String id);

    void updateGameField(String game_id, String[][] newField);

    //void updateGameField(String id, String[][] newField);
    boolean deleteGameInfo(String id);
}
