package org.example.minesweeper.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("gameInfoService")
public class DefaultGameInfoService implements GameInfoService {
    private final GameInfoRepository gameInfoRepository;

    public DefaultGameInfoService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    @Override
    public void saveGameInfo(GameInfoData gameInfoData) {
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        String uuid = UUID.randomUUID().toString().toUpperCase();
        gameInfoResponse.setWidth(gameInfoData.getWidth());
        gameInfoResponse.setHeight(gameInfoData.getHeight());
        gameInfoResponse.setMines_count(gameInfoData.getMines_count());
        gameInfoResponse.setCompleted(gameInfoData.isCompleted());
        gameInfoResponse.setGame_id(uuid);
        gameInfoResponse = gameInfoRepository.save(gameInfoResponse);
        gameInfoData.setGame_id(gameInfoResponse.getGame_id());
    }

    @Override
    public GameInfoData getGameInfo(String game_id) {
        return gameInfoRepository.findById(Long.valueOf(game_id))
                .map(gameInfoResponse -> {
                    // Создание объекта GameInfoData из объекта GameInfoResponse и объекта NewGameRequest
                    GameInfoData gameInfoData = new GameInfoData();
                    gameInfoData.setGame_id(gameInfoResponse.getGame_id());
                    gameInfoData.setWidth(gameInfoResponse.getWidth());
                    gameInfoData.setHeight(gameInfoResponse.getHeight());
                    gameInfoData.setMines_count(gameInfoResponse.getMines_count());
                    gameInfoData.setCompleted(gameInfoResponse.isCompleted());
                    return gameInfoData;
                })
                .orElseThrow(() -> new EntityNotFoundException("GameInfo with id " + game_id + " not found"));
    }

    @Override
    public boolean deleteGameInfo(String game_id) {
        if (gameInfoRepository.existsById(Long.valueOf(game_id))) {
            gameInfoRepository.deleteById(Long.valueOf(game_id));
            return true;
        }
        return false;
    }
}
