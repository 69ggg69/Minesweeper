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
@RequestMapping("/api")
public class FirsController {
    private final DefaultGameInfoService gameInfoService;

    public FirsController(DefaultGameInfoService gameInfoService) {
        this.gameInfoService = gameInfoService;
    }
//    @Resource(name = "gameInfoService")
//    private GameInfoService gameInfoService;

    @PostMapping("/new")
        public ResponseEntity<GameInfoData> newGameRequest(@RequestBody GameInfoData gameInfoData) throws BindException {
            int width = gameInfoData.getWidth();
            if (width < 2 || width> 30) {
                throw new BindException("ширина поля должна быть не менее 2 и не более 30");
            }
            int height = gameInfoData.getHeight();
            if (height < 2 || height > 30) {
                throw new BindException("высота поля должна быть не менее 2 и не более 30");
            }
            int maxMines = width * height -1;
            int mines = gameInfoData.getMines_count();
            if (mines < 1 || mines > maxMines) {
                throw new BindException("количество мин должно быть не менее 1 и не более " + (maxMines));
            }
        gameInfoService.saveGameInfo(gameInfoData);
        return new ResponseEntity<>(gameInfoData, HttpStatus.CREATED);


//            GameInfoResponse gameInfoResponse = new GameInfoResponse(newGameRequest);

//            return new ResponseEntity<GameInfoResponse>(gameInfoResponse, HttpStatus.OK);
          //  return new ResponseEntity<GameInfoResponse>(new GameInfoResponse(game_id, width, height, mines), HttpStatus.OK);
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



