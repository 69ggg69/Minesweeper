package org.example.minesweeper;

import jakarta.persistence.EntityNotFoundException;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.example.minesweeper.services.GameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameInfoService")
public class DefaultGameInfoService implements GameInfoService {
   @Autowired
    private GameInfoRepository gameInfoRepository;
    @Override
    public GameInfoData saveGameInfo(GameInfoData gameInfoData) {
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        gameInfoResponse.setWidth(gameInfoData.getWidth());
        gameInfoResponse.setHeight(gameInfoData.getHeight());
        gameInfoResponse.setMines_count(gameInfoData.getMines_count());
        gameInfoResponse.setCompleted(gameInfoData.isCompleted());

        // Сохранение объекта GameInfo в базе данных с помощью репозитория
        gameInfoResponse = gameInfoRepository.save(gameInfoResponse);

        // Обновление идентификатора в gameInfoData
        gameInfoData.setGame_id(gameInfoResponse.getGame_id());

        // Возвращение сохраненного объекта gameInfoData
        return gameInfoData;
    }

    @Override
    public GameInfoData getGameInfo(Long game_id, NewGameRequest newGameRequest) {
        return gameInfoRepository.findById(game_id)
                .map(gameInfoResponse -> {
                    GameInfoData gameInfoData = new GameInfoData();
                    gameInfoData.setGame_id(gameInfoResponse.getGame_id());
                    gameInfoData.setWidth(newGameRequest);
                    gameInfoData.setHeight(newGameRequest);
                    gameInfoData.setMines_count(newGameRequest);
                    gameInfoData.setCompleted(gameInfoResponse.isCompleted());
                    return gameInfoData;
                })
                .orElseThrow(() -> new EntityNotFoundException("GameInfo with id " + game_id + " not found"));
    }

    @Override
    public boolean deleteGameInfo(Long game_id) {
        if (gameInfoRepository.existsById(game_id)) {
            gameInfoRepository.deleteById(game_id);
            return true;
        }
        return false;
    }



}
