package org.example.minesweeper.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecondController {
    @PostMapping("/api/turn")
    public void gameTurnRequest() {

    }
}
