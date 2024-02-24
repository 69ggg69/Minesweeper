package org.example.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GameInfoData {
    private String game_id;
    private int width;
    private int height;
    private int mines_count;
    @JsonIgnore
    private String fieldString;
    private boolean completed;
    public String[][] getField() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String[][] fieldArray = objectMapper.readValue(fieldString, String[][].class);
            for (int i = 0; i < fieldArray.length; i++) {
                for (int j = 0; j < fieldArray[i].length; j++) {
                    if (fieldArray[i][j] == null) {
                        fieldArray[i][j] = " ";
                    }
                }
            }
            return fieldArray;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }


    public void setField(String[][] field) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            fieldString = objectMapper.writeValueAsString(field);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


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

    public String getFieldString() {
        return fieldString;
    }

    public void setFieldString(String fieldString) {
        this.fieldString = fieldString;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
