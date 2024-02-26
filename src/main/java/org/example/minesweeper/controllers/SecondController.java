package org.example.minesweeper.controllers;

import org.example.minesweeper.GameTurnRequest;
import org.example.minesweeper.createTable.GameInfoResponse;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.services.MinesweeperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SecondController {
    private final MinesweeperService minesweeperService;
    public SecondController(MinesweeperService minesweeperService) {
        this.minesweeperService = minesweeperService;
    }
    @CrossOrigin(origins = "https://minesweeper-test.studiotg.ru/")
    @PostMapping("/turn")
    public ResponseEntity<GameInfoData> makeTurn(@RequestBody GameTurnRequest gameTurnRequest) {
        GameInfoData gameInfoData = minesweeperService.makeTurn(gameTurnRequest.getGame_id(), gameTurnRequest);
        return ResponseEntity.ok(gameInfoData);
    }
}

