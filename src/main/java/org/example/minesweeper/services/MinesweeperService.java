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
    public GameInfoResponse getGameInfo(String gameId) {
        return gameInfoRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("GameInfoResponse with id " + gameId + " not found"));
    }

    public GameInfoData makeTurn(String gameId, GameTurnRequest gameTurnRequest) {
        GameInfoResponse gameInfoResponse = gameInfoRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("GameInfoResponse with id " + gameId + " not found"));
        int row = gameTurnRequest.getRow();
        int col = gameTurnRequest.getCol();
        openCell(gameInfoResponse, row, col);
        return convertToGameInfoData(gameInfoResponse);
    }

    private void openCell(GameInfoResponse gameInfoResponse, int row, int col) {
            String[][] field = deserializeField(gameInfoResponse.getField());
            if (row < 0 || row >= field.length || col < 0 || col >= field[0].length) {
                throw new IllegalArgumentException("Invalid cell coordinates");
            }
            if (!field[row][col].equals(" ") || gameInfoResponse.isCompleted()) {
                return;
            }
            if (isFieldEmpty(field)) {
                field = generateField(gameInfoResponse.getHeight(), gameInfoResponse.getWidth(), gameInfoResponse.getMines_count());
                gameInfoResponse.setField(serializeField(field));
            }
        openCellRecursive(field, row, col);
        gameInfoResponse.setField(serializeField(field));
        checkGameCompletion(gameInfoResponse); // Проверяем завершение игры после открытия ячейки
        gameInfoRepository.save(gameInfoResponse);

    }

    private boolean isFieldEmpty(String[][] field) {
        for (String[] row : field) {
            for (String cell : row) {
                if (!cell.equals(" ") && !cell.equals("M"))  {
                    return false; // Найдена непустая ячейка
                }
            }
        }
        return true; // Все ячейки пусты
    }

    private void openCellRecursive(String[][] field, int row, int col) {
        if (row < 0 || row >= field.length || col < 0 || col >= field[0].length || !field[row][col].equals(" ")) {
            return; // Проверяем границы поля и уже открытые ячейки
        }
        if (field[row][col].equals("M")) {
            return; // Если текущая ячейка - мина, просто возвращаемся
        }
        int adjacentMines = countAdjacentMines(field, row, col);
        if (adjacentMines > 0) {
            field[row][col] = Integer.toString(adjacentMines);
        } else {
            field[row][col] = "0";
            for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(field[0].length - 1, col + 1); j++) {
                    if (i == row && j == col) continue; // Игнорируем текущую ячейку
                    openCellRecursive(field, i, j);
                }
            }
        }
    }
    private String[][] generateField(int height, int width, int minesCount) {
        String[][] field = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = " ";
            }
        }
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < minesCount) {
            int randRow = random.nextInt(height);
            int randCol = random.nextInt(width);
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
        String[][] field = deserializeField(gameInfoResponse.getField());
        int minesCount = gameInfoResponse.getMines_count();
        int openedCount = 0;
        int totalCells = field.length * field[0].length;
        for (String[] row : field) {
            for (String cell : row) {
                if (!cell.equals(" ") && !cell.equals("M")) {
                    openedCount++;
                }
            }
        }
        if (openedCount == totalCells - minesCount || openedCount == totalCells) {
            gameInfoResponse.setCompleted(true);
            gameInfoRepository.save(gameInfoResponse);
        }
    }
    private String[][] deserializeField(String fieldString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(fieldString, String[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing field data", e);
        }
    }
    private String serializeField(String[][] field) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(field);
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
    //    private void performGameTurn(GameInfoData gameInfoData, int row, int col) {
//        String[][] field = gameInfoData.getField();
//        openCell(gameInfoData.getField(), row, col);
//        gameInfoData.setField(field);
//        checkGameCompletion(gameInfoData);
//    }

//    private void openCellRecursive(String[][] field, int row, int col) {
//        if (row < 0 || row >= field.length || col < 0 || col >= field[0].length || !field[row][col].equals(" ")) {
//            return;
//        }
//        if (field[row][col].equals("M")) {
//            return;
//        }
//        int adjacentMines = countAdjacentMines(field, row, col);
//        field[row][col] = Integer.toString(adjacentMines);
//        if (adjacentMines == 0) {
//            for (int i = Math.max(0, row - 1); i <= Math.min(field.length - 1, row + 1); i++) {
//                for (int j = Math.max(0, col - 1); j <= Math.min(field[0].length - 1, col + 1); j++) {
//                    openCellRecursive(field, i, j);
//                }
//            }
//        }
//    }
//        private void placeMines(String[][] field, int minesCount) {
//        Random random = new Random();
//        int rows = field.length;
//        int cols = field[0].length;
//        int placedMines = 0;
//        while (placedMines < minesCount) {
//            int randRow = random.nextInt(rows);
//            int randCol = random.nextInt(cols);
//
//            if (field[randRow][randCol].equals(" ")) {
//                field[randRow][randCol] = "M"; // Устанавливаем мину
//                placedMines++;
//            }
//        }
//    }
}

