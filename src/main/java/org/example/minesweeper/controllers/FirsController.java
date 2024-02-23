package org.example.minesweeper.controllers;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.services.DefaultGameInfoService;
import org.example.minesweeper.services.GameInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.BindException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/new")
public class FirsController {
    private final DefaultGameInfoService gameInfoService;

    public FirsController(DefaultGameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }
    @CrossOrigin(origins = "https://minesweeper-test.studiotg.ru/")
    @PostMapping
        public ResponseEntity<GameInfoData> newGameRequest(@RequestBody GameInfoData gameInfoData) throws BindException {
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
        gameInfoData.setMines_count(mines);
        gameInfoData.setWidth(width);
        gameInfoData.setHeight(height);
        gameInfoService.saveGameInfo(gameInfoData);
        return new ResponseEntity<>(gameInfoData, HttpStatus.CREATED);
    }


@ExceptionHandler(BindException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBindException(BindException ex, HttpServletResponse response) throws IOException {
        String errorMessage = "error: " + ex.getMessage();
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMessage);
        response.getWriter().write(errorMessage);
        return ResponseEntity.badRequest().build();
}
}



