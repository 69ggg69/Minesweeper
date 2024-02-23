package org.example.minesweeper.services;

import org.example.minesweeper.dto.GameInfoData;

public interface GameInfoService {
    void saveGameInfo(GameInfoData gameInfoData);
    GameInfoData getGameInfo(String id);
    boolean deleteGameInfo(String id);
}
