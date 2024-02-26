package org.example.minesweeper.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.repositories.GameInfoRepository;
import org.example.minesweeper.services.MinesweeperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


@RestController
@RequestMapping
public class FirsController {
    private final MinesweeperService gameInfoService;
    public FirsController(MinesweeperService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }
    @CrossOrigin(origins = "https://minesweeper-test.studiotg.ru/")
    @PostMapping("/new")

    public ResponseEntity<?> newGameRequest(@RequestBody GameInfoData gameInfoData) throws BindException {
        try {
            int width = gameInfoData.getWidth();
            if (width < 2 || width > 30) {
                throw new BindException("ширина поля должна быть не менее 2 и не более 30");
            }
            int height = gameInfoData.getHeight();
            if (height < 2 || height > 30) {
                throw new BindException("высота поля должна быть не менее 2 и не более 30");
            }
            int maxMines = width * height - 1;
            int mines = gameInfoData.getMines_count();
            if (mines < 1 || mines > maxMines) {
                throw new BindException("количество мин должно быть не менее 1 и не более " + (maxMines));
            }
            String gameId = gameInfoService.generateGameId();
            gameInfoData.setGame_id(gameId);
            gameInfoData.setMines_count(mines);
            gameInfoData.setWidth(width);
            gameInfoData.setHeight(height);
            gameInfoData.setField(new String[height][width]);
            gameInfoService.save(gameInfoService.convertToGameInfoResponse(gameInfoData));
            return ResponseEntity.created(URI.create("/new/" + gameInfoData.getGame_id())).body(gameInfoData);
        } catch (BindException ex) {
            return handleBindException(ex);
        }
    }

    @ExceptionHandler(BindException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ResponseEntity<?> handleBindException(BindException ex) {
    String errorMessage = "error: " + ex.getMessage();
    return ResponseEntity.badRequest().body(errorMessage);
}
}

