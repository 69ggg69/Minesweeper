package org.example.minesweeper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.UUID;

import java.util.List;
@JsonPropertyOrder({"game_id", "width", "height", "mines_count", "completed", "field"})

public class GameInfoResponse extends NewGameRequest{
    private GameInfoResponse gameInfoResponse;

    public GameInfoResponse(NewGameRequest newGameRequest) {
        super(newGameRequest.getWidth(), newGameRequest.getHeight(), newGameRequest.getMines_count());
        setGame_id(UUID.randomUUID().toString().toUpperCase());

    }
    private String game_id;
    private boolean completed;
    private List<List<String>> field;
    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<List<String>> getField() {
        return field;
    }

    public void setField(List<List<String>> field) {
        this.field = field;
    }
//    public static String generateRandomId() {
//        UUID uuid = UUID.randomUUID();
//        long mostSignificantBits = uuid.getMostSignificantBits();
//        long leastSignificantBits = uuid.getLeastSignificantBits();
//        String formattedId = String.format("%08X-%04X-%04X-%04X-%012X",
//                (mostSignificantBits >> 32) & 0xFFFFFFFFL,
//                (mostSignificantBits >> 16) & 0xFFFFL,
//                mostSignificantBits & 0xFFFFL,
//                (leastSignificantBits >> 48) & 0xFFFFL,
//                leastSignificantBits & 0xFFFFFFFFFFFFL);
//        return formattedId;
//    }
}
