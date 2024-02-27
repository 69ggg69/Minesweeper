package org.example.minesweeper.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.minesweeper.GameTurnRequest;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
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
                .orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
        int row = gameTurnRequest.getRow();
        int col = gameTurnRequest.getCol();
        openCell(gameInfoResponse, row, col);
        return convertToGameInfoData(gameInfoResponse);
    }
    private void openCell(GameInfoResponse gameInfoResponse, int row, int col) {
        String[][] visibleField = deserializeField(gameInfoResponse);
        String[][] programField = deserializeField(gameInfoResponse);
        if (row < 0 || row >= programField.length || col < 0 || col >= programField[0].length) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (!visibleField[row][col].equals(" ") || gameInfoResponse.isCompleted()) {
            return;
        }
         if (isFieldEmpty(programField)) {
             programField = generateField(gameInfoResponse.getHeight(), gameInfoResponse.getWidth(), gameInfoResponse.getMines_count(), row, col);
         }
        openCellRecursive(programField,visibleField, row, col);
        updateVisibleField(visibleField, programField);
        gameInfoResponse.setField(serializeField(visibleField));
        checkGameCompletion(gameInfoResponse);
        gameInfoRepository.save(gameInfoResponse);
    }
    private void updateVisibleField(String[][] visibleField, String[][] programField) {
        for (int row = 0; row < programField.length; row++) {
            for (int col = 0; col < programField[row].length; col++) {
                if (!programField[row][col].equals("M") && !visibleField[row][col].equals(programField[row][col])) {
                    visibleField[row][col] = programField[row][col];
                }
            }
        }
    }
    private boolean isFieldEmpty(String[][] field) {
        for (String[] row : field) {
            for (String cell : row) {
                if (!cell.equals(" ") && !cell.equals("M")) {
                    return false;
                }
            }
        }
        return true;
    }
    private void openCellRecursive(String[][] visibleField, String[][] programField, int row, int col) {
        if (row < 0 || row >= programField.length || col < 0 || col >= programField[0].length || !visibleField[row][col].equals(" ")) {
            return;
        }
        if (!programField[row][col].equals("0")) {
            visibleField[row][col] = programField[row][col];
            return;
        }
        visibleField[row][col] = "0";
        for (int i = Math.max(0, row - 1); i <= Math.min(programField.length - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(programField[0].length - 1, col + 1); j++) {
                if (i == row && j == col) continue;
                if (programField[i][j].equals("0")) {
                    openCellRecursive(visibleField, programField, i, j);
                } else {
                    visibleField[i][j] = programField[i][j];
                }
            }
        }
    }
    private String[][] generateField(int height, int width, int minesCount, int firstClickRow, int firstClickCol) {
        String[][] field = new String[height][width];
        Random random = new Random();
        int minesPlaced = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = " ";
            }
        }
        while (minesPlaced < minesCount) {
            int randRow = random.nextInt(height);
            int randCol = random.nextInt(width);
            if (Math.abs(randRow - firstClickRow) <= 1 && Math.abs(randCol - firstClickCol) <= 1) {
                continue;
            }
            if (!field[randRow][randCol].equals("M")) {
                field[randRow][randCol] = "M";
                minesPlaced++;
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!field[i][j].equals("M")) {
                    int adjacentMines = countAdjacentMines(field, i, j);
                    field[i][j] = Integer.toString(adjacentMines);
                }
            }
        }
        return field;
    }

    private int countAdjacentMines(String[][] field, int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(field[0].length - 1, col + 1); j++) {
                if (field[i][j].equals("M")) {
                    count++;
                }
            }
        }
        return count;
    }
    private void checkGameCompletion(GameInfoResponse gameInfoResponse) {
        String[][] field = deserializeField(gameInfoResponse);
        int minesCount = gameInfoResponse.getMines_count();
        int openedCount = 0;
        if (field != null) {
            int totalCells = field.length * field[0].length;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[0].length; j++) {
                    if (!field[i][j].equals(" ") && !field[i][j].equals("M")) {
                        openedCount++;
                    }
                }
            }
            if (openedCount == totalCells - minesCount) {
                gameInfoResponse.setCompleted(true);
            }
        }
    }
    private String[][] deserializeField(GameInfoResponse gameInfoResponse) {
        String[][] programField;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            programField = objectMapper.readValue(gameInfoResponse.getField(), String[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing field data", e);
        }
        return programField;
    }

    private String serializeField(String[][] programField) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(programField);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while serializing field data", e);
        }
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

