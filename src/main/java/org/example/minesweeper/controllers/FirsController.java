package org.example.minesweeper.controllers;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.services.GameInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.minesweeper.NewGameRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.BindException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping()
public class FirsController {
    @Resource(name = "gameInfoService")
    private GameInfoService gameInfoService;


    @PostMapping("/api")
        public ResponseEntity<GameInfoResponse> newGameRequest(@RequestBody NewGameRequest newGameRequest) throws BindException {
            int width = newGameRequest.getWidth();
            if (width < 2 || width> 30) {
                throw new BindException("ширина поля должна быть не менее 2 и не более 30");
            }
            int height = newGameRequest.getHeight();
            if (height < 2 || height > 30) {
                throw new BindException("высота поля должна быть не менее 2 и не более 30");
            }
            int maxMines = width * height -1;
            int mines = newGameRequest.getMines_count();
            if (mines < 1 || mines > maxMines) {
                throw new BindException("количество мин должно быть не менее 1 и не более " + (maxMines));
            }
            GameInfoData gameInfoData = new GameInfoData();
            gameInfoData.setHeight(newGameRequest);
            gameInfoData.setWidth(newGameRequest);
            gameInfoData.setMines_count(newGameRequest);
            gameInfoService.saveGameInfo(gameInfoData);
            return new ResponseEntity<GameInfoResponse>(new GameInfoResponse(), HttpStatus.OK);

//            GameInfoResponse gameInfoResponse = new GameInfoResponse(newGameRequest);

//            return new ResponseEntity<GameInfoResponse>(gameInfoResponse, HttpStatus.OK);
          //  return new ResponseEntity<GameInfoResponse>(new GameInfoResponse(game_id, width, height, mines), HttpStatus.OK);
    }
    @GetMapping("/api/new")



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



