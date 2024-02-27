package org.example.minesweeper.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;

public class GameInfoData {
    private String game_id;
    @JsonCreator
    public GameInfoData(@JsonProperty("game_id") String game_id) {
    this.game_id = game_id;
}
    private int width;
    private int height;
    private int mines_count;
    @JsonIgnore
    private String fieldString;

    public GameInfoData() {

    }

    public String[][] getField() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            if (fieldString == null) {
                return new String[height][width];
            }
            return objectMapper.readValue(this.fieldString, String[][].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while deserializing field data", e);
        }
    }

    public void setField(String[][] field) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            for (String[] row : field) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] == null) {
                        row[i] = " ";
                    }
                }
            }

            fieldString = objectMapper.writeValueAsString(field);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

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
