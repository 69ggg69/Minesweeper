package org.example.minesweeper.controllers;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.minesweeper.NewGameRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.BindException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping()
public class FirsController {

    @PostMapping("/api/new")
        public ResponseEntity<NewGameRequest> newGameRequest(@RequestBody NewGameRequest newGameRequest) throws BindException {
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
        return new ResponseEntity<NewGameRequest>(newGameRequest, HttpStatus.OK);
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



