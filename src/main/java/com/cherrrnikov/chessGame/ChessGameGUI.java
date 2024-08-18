package com.cherrrnikov.chessGame;

import com.cherrrnikov.chessGame.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class ChessGameGUI extends JFrame {
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();
    private Map<Class<? extends Piece>, String> pieceUnicodeMap = new HashMap<>() {
        {
            put(Pawn.class, "\u265F");
            put(Rook.class, "\u265C");
            put(Knight.class, "\u265E");
            put(Bishop.class, "\u265D");
            put(Queen.class, "\u265B");
            put(King.class, "\u265A");
        }
    };

    public ChessGameGUI() {
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeBoard();
        pack(); // Adjust window size to fit the chessboard
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalCol = col;
                final int finalRow = row;
                ChessSquareComponent squareComponent = new ChessSquareComponent(row, col);
                squareComponent.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });
                add(squareComponent);
                squares[row][col] = squareComponent;
            }
        }
        refreshBoard();
    }

    private void refreshBoard() {
        ChessBoard board = game.getBoard();
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                Piece piece = board.getPiece(row, col); // Assuming ChessBoard has a getPiece method
                if (piece != null) {
                    // If using Unicode symbols:
                    String symbol = pieceUnicodeMap.get(piece.getClass());
                    Color color = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
                    squares[row][col].setPieceSymbol(symbol, color);
                    // Or, if updating with icons or any other graphical representation
                } else {
                    squares[row][col].clearPieceSymbol();
                }
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        if (game.handleSquareSelection(row, col)) {
            refreshBoard();
            checkGameState();
        }
    }

    private void checkGameState() {
        PieceColor currentPlayer = game.getCurrentPlayerColor(); // This method should return the current player's color

        if (game.isInCheck(currentPlayer)) {
            JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGameGUI::new);
    }
}
