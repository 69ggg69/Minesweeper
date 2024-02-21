package org.example.minesweeper;

import com.fasterxml.jackson.annotation.JsonTypeId;

public class NewGameRequest {
    NewGameRequest newGameRequest;
    public NewGameRequest(int width, int height, int mines_count) {
        this.width = width;
        this.height = height;
        this.mines_count = mines_count;
    }


    private int width;
    private int height;
    private int mines_count;


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


}
