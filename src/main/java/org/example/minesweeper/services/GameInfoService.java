package org.example.minesweeper.services;

import org.example.minesweeper.NewGameRequest;
import org.example.minesweeper.dto.GameInfoData;

public interface GameInfoService {
    GameInfoData saveGameInfo(GameInfoData gameInfoData);
    GameInfoData getGameInfo(Long id, NewGameRequest newGameRequest);
    boolean deleteGameInfo(Long id);
}
