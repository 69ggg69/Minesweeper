package org.example.minesweeper.createTable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "game_info")
public class GameInfoResponse {
    @Id
    private String game_id;

    public GameInfoResponse() {

    }
    @Column
    private int width;
    @Column
    private int height;
    @Column
    private int mines_count;
    @Column
    private boolean completed;

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMines_count() {
        return mines_count;
    }

    public void setMines_count(int mines_count) {
        this.mines_count = mines_count;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

