package com.cherrrnikov.chessGame.pieces;

import com.cherrrnikov.chessGame.PieceColor;
import com.cherrrnikov.chessGame.Position;

public abstract class Piece {
    protected Position position;
    protected PieceColor color;

    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PieceColor getColor() {
        return color;
    }

    public abstract boolean isValidMove(Position newPosition, Piece[][] board);
}
