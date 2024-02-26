package org.example.minesweeper;


public class GameTurnRequest{
    private int row;
    private int col;
    private String game_id;

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    DefaultGameInfoService gameInfoService;

//    public void openAdjacentCells() {
//        String[][] field = getField();
//        if (field[row][col].equals(" ")) {
//            field[row][col] = "0";
//            openAdjacentCellsRecursive(field, row, col);
//        }
//        gameInfoService.updateGameField(getGame_id(), field);
//    }
//
//    private void openAdjacentCellsRecursive(String[][] field, int row, int col) {
//        for (int i = row - 1; i <= row + 1; i++) {
//            for (int j = col - 1; j <= col + 1; j++) {
//                if (i >= 0 && i < field.length && j >= 0 && j < field[0].length) {
//                    if (field[i][j].equals(" ")) {
//                        field[i][j] = "0";
//                        openAdjacentCellsRecursive(field, i, j);
//                    }
//                }
//            }
        }

