package org.example.minesweeper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.minesweeper.GameTurnRequest;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service("minesweeperService")
public class MinesweeperService {
    private final GameInfoRepository gameInfoRepository;

    public MinesweeperService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }
    public void save(GameInfoResponse gameInfoResponse) {
        this.gameInfoRepository.save(gameInfoResponse);
    }
    public String generateGameId() {
        return UUID.randomUUID().toString();
    }

    public GameInfoData makeTurn(String gameId, GameTurnRequest gameTurnRequest) {
        GameInfoResponse gameInfoResponse = gameInfoRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("GameInfoResponse with id " + gameId + " not found"));
        GameInfoData gameInfoData = convertToGameInfoData(gameInfoResponse);
        int row = gameTurnRequest.getRow() - 1;
        int col = gameTurnRequest.getCol() - 1;
        GameInfoData updatedGameInfoData = performGameTurn(gameInfoData, row, col);
        gameInfoRepository.save(convertToGameInfoResponse(updatedGameInfoData));
        return updatedGameInfoData;
    }

    private GameInfoData performGameTurn(GameInfoData gameInfoData, int row, int col) {
        String[][] field = gameInfoData.getField();
        openCell(field, row, col);
        gameInfoData.setField(field);
        checkGameCompletion(gameInfoData);
        return gameInfoData;
    }

    private void openCell(String[][] field, int row, int col) {
        if (row < 0 || row >= field.length || col < 0 || col >= field[0].length) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (!field[row][col].equals(" ")) {
            return;
        }
        field[row][col] = "0";
        for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(field[0].length - 1, col + 1); j++) {
                openCell(field, i, j);
            }
        }
    }

    private void checkGameCompletion(GameInfoData gameInfoData) {
        String[][] field = gameInfoData.getField();
        for (String[] strings : field) {
            for (int j = 0; j < field[0].length; j++) {
                if (strings[j].equals("M")) {
                    return;
                }
            }
        }
        gameInfoData.setCompleted(true);
    }

    public GameInfoData convertToGameInfoData(GameInfoResponse gameInfoResponse) {
        GameInfoData gameInfoData = new GameInfoData();
        gameInfoData.setGame_id(gameInfoResponse.getGame_id());
        gameInfoData.setWidth(gameInfoResponse.getWidth());
        gameInfoData.setHeight(gameInfoResponse.getHeight());
        gameInfoData.setMines_count(gameInfoResponse.getMines_count());
        gameInfoData.setCompleted(gameInfoResponse.isCompleted());
        gameInfoData.setFieldString(gameInfoResponse.getField());
        return gameInfoData;
    }

    private String convertFieldToString(String[][] field) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(field);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while serializing field data", e);
        }
    }

    public GameInfoResponse convertToGameInfoResponse(GameInfoData gameInfoData) {
        GameInfoResponse gameInfoResponse = new GameInfoResponse();
        gameInfoResponse.setGame_id(gameInfoData.getGame_id());
        gameInfoResponse.setWidth(gameInfoData.getWidth());
        gameInfoResponse.setHeight(gameInfoData.getHeight());
        gameInfoResponse.setMines_count(gameInfoData.getMines_count());
        gameInfoResponse.setCompleted(gameInfoData.isCompleted());
        gameInfoResponse.setField(convertFieldToString(gameInfoData.getField()));
        return gameInfoResponse;
    }
}

