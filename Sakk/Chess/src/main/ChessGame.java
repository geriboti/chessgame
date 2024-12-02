package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import PGNHandling.PGNReader;
import PGNHandling.PGNWriter;
import piece.PieceColor;

public class ChessGame {

    private static List<String> moves = new ArrayList<>();
    private static final String FILE_PATH = "C:/_3._félév/A programozás alapjai 3/Sakk/game.pgn"; // Az alapértelmezett mentési fájl útvonala
    private static ChessBoard chessBoard;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sakk Játék");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        chessBoard = new ChessBoard();
        frame.add(chessBoard);

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Játék");
        JMenuItem newGameItem = new JMenuItem("Új játék");
        JMenuItem newGameBotItem = new JMenuItem("Új játék bot-tal"); // Új menüelem hozzáadása
        JMenuItem loadGameItem = new JMenuItem("Játék betöltése");
        JMenuItem saveGameItem = new JMenuItem("Játék mentése");
        JMenuItem exitItem = new JMenuItem("Kilépés");

        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(false);
            }
        });

        newGameBotItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame(true); // Bot játék indítása
            }
        });

        loadGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        saveGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameMenu.add(newGameItem);
        gameMenu.add(newGameBotItem); // Új menüelem hozzáadása
        gameMenu.add(loadGameItem);
        gameMenu.add(saveGameItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }


    private static void startNewGame(boolean isBotGame) {
        moves.clear();
        chessBoard.startNewGame(isBotGame);
        System.out.println("Új játék indítva! Bot ellen: " + isBotGame);
    }

    private static void loadGame() {
        PieceColor currentPlayer = PGNReader.loadGame(chessBoard.getBoard(), FILE_PATH);
        chessBoard.setCurrentPlayerColor(currentPlayer);
        chessBoard.updateBoard(); // Frissíti a sakktábla megjelenítését
        System.out.println("Játék betöltve!");
    }

    public static void saveGame() {
        PGNWriter.saveGame(chessBoard.getBoard(), FILE_PATH, chessBoard.getCurrentPlayerColor());
        System.out.println("Játék mentve: " + FILE_PATH);
    }
}
