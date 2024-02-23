package org.example.minesweeper.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.springframework.stereotype.Service;

@Service("gameInfoService")
public class DefaultGameInfoService implements GameInfoService {
    private final GameInfoRepository gameInfoRepository;

    public DefaultGameInfoService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    @Override
    public void saveGameInfo(GameInfoData gameInfoData) {
        // Создание объекта GameInfoResponse из объекта GameInfoData
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        gameInfoResponse.setWidth(gameInfoData.getWidth());
        gameInfoResponse.setHeight(gameInfoData.getHeight());
        gameInfoResponse.setMines_count(gameInfoData.getMines_count());
        gameInfoResponse.setCompleted(gameInfoData.isCompleted());

        // Сохранение объекта GameInfoResponse в репозитории и обновление game_id в объекте GameInfoData
        gameInfoResponse = gameInfoRepository.save(gameInfoResponse);
        gameInfoData.setGame_id(gameInfoResponse.getGame_id());
    }

    @Override
    public GameInfoData getGameInfo(Long game_id) {
        // Получение объекта GameInfoResponse по game_id из репозитория
        return gameInfoRepository.findById(game_id)
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
    public boolean deleteGameInfo(Long game_id) {
        // Проверка наличия записи с заданным game_id в репозитории и удаление, если найден
        if (gameInfoRepository.existsById(game_id)) {
            gameInfoRepository.deleteById(game_id);
            return true;
        }
        return false;
    }
}
