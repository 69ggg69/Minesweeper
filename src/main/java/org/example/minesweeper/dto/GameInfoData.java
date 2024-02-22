package org.example.minesweeper.dto;

import org.example.minesweeper.NewGameRequest;

public class GameInfoData {
    private Long game_id;
    private int width;
    private int height;
    private int mines_count;
    private boolean completed;

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(NewGameRequest newGameRequest) {
        this.width = newGameRequest.getWidth();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(NewGameRequest newGameRequest) {
        this.height = newGameRequest.getHeight();
    }

    public int getMines_count() {
        return mines_count;
    }

    public void setMines_count(NewGameRequest newGameRequest) {
        this.mines_count = newGameRequest.getMines_count();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
