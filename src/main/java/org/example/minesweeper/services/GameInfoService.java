package org.example.minesweeper.services;

import org.example.minesweeper.dto.GameInfoData;

public interface GameInfoService {
    void saveGameInfo(GameInfoData gameInfoData);
    GameInfoData getGameInfo(Long id);
    boolean deleteGameInfo(Long id);
}
