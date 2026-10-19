import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    private final JButton[][] cells = new JButton[3][3];
    private final JLabel status = new JLabel("Player X's turn", SwingConstants.CENTER);
    private final JLabel scores = new JLabel("X: 0    O: 0    Draws: 0", SwingConstants.CENTER);
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private int xWins = 0, oWins = 0, draws = 0;

    public TicTacToe() {
        super("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 530);
        setMinimumSize(new Dimension(350, 430));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new GridLayout(2, 1));
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        scores.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        header.add(status);
        header.add(scores);
        add(header, BorderLayout.NORTH);

        JPanel board = new JPanel(new GridLayout(3, 3, 6, 6));
        board.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        board.setBackground(new Color(40, 54, 74));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton button = new JButton("");
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 68));
                button.setFocusPainted(false);
                button.setBackground(Color.WHITE);
                final int row = r, col = c;
                button.addActionListener(e -> play(row, col));
                cells[r][c] = button;
                board.add(button);
            }
        }
        add(board, BorderLayout.CENTER);

        JButton newGame = new JButton("New Game");
        newGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        newGame.addActionListener(e -> resetBoard());
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        footer.add(newGame, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }

    private void play(int row, int col) {
        if (gameOver || !cells[row][col].getText().isEmpty()) return;
        JButton button = cells[row][col];
        button.setText(String.valueOf(currentPlayer));
        button.setForeground(currentPlayer == 'X' ? new Color(36, 99, 190) : new Color(208, 66, 75));

        if (hasWon(currentPlayer)) {
            gameOver = true;
            status.setText("Player " + currentPlayer + " wins!");
            if (currentPlayer == 'X') xWins++; else oWins++;
            updateScores();
        } else if (boardFull()) {
            gameOver = true;
            draws++;
            status.setText("It's a draw!");
            updateScores();
        } else {
            currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
            status.setText("Player " + currentPlayer + "'s turn");
        }
    }

    private boolean hasWon(char player) {
        String p = String.valueOf(player);
        for (int i = 0; i < 3; i++) {
            if (matches(p, i, 0, i, 1, i, 2) || matches(p, 0, i, 1, i, 2, i)) return true;
        }
        return matches(p, 0, 0, 1, 1, 2, 2) || matches(p, 0, 2, 1, 1, 2, 0);
    }

    private boolean matches(String p, int r1, int c1, int r2, int c2, int r3, int c3) {
        return cells[r1][c1].getText().equals(p)
            && cells[r2][c2].getText().equals(p)
            && cells[r3][c3].getText().equals(p);
    }

    private boolean boardFull() {
        for (JButton[] row : cells) {
            for (JButton cell : row) {
                if (cell.getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void updateScores() {
        scores.setText("X: " + xWins + "    O: " + oWins + "    Draws: " + draws);
    }

    private void resetBoard() {
        for (JButton[] row : cells) {
            for (JButton cell : row) cell.setText("");
        }
        currentPlayer = 'X';
        gameOver = false;
        status.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe().setVisible(true));
    }
}
