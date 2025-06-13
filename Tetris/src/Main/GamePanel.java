package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Database.DatabaseManager;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 640;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    public static Sound music = new Sound();
    public static Sound se = new Sound();
    public static JButton restartButton;
 //   private ScorePanel scorePanel;
    private boolean gameOverHandled = false; // ✅ Pour éviter les répétitions

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pm = new PlayManager();
        setupRestartButton();

      //  scorePanel = new ScorePanel();
     //   scorePanel.setBounds(WIDTH - 300, 50, 250, 400);
     //   this.add(scorePanel);
    }

    private void setupRestartButton() {
        restartButton = new JButton("Replay");
        restartButton.setBounds(WIDTH / 2 - 60, HEIGHT / 2 + 50, 120, 40);
        restartButton.setFocusable(false);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        this.add(restartButton);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();

        music.play(0, true);
        music.loop();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (!KeyHandler.pausePressed) {
            if (!pm.gameOver) {
                pm.update();
            } else if (!gameOverHandled) {
                gameOverHandled = true; // ✅ Empêche l'exécution répétée
                SwingUtilities.invokeLater(() -> {
                    String playerName = JOptionPane.showInputDialog(
                        null,
                        "Entrez votre nom :",
                        "Game Over",
                        JOptionPane.PLAIN_MESSAGE
                    );

                    if (playerName != null && !playerName.trim().isEmpty()) {
                        DatabaseManager db = new DatabaseManager();
                     //   db.insertScore(playerName.trim(), pm.score);
                        db.insertIfHighScore(playerName, pm.score);

                       

                       // scorePanel.loadScores();
                       // scorePanel.repaint();
                    }

                    restartButton.setVisible(true);
                });
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        pm.draw(g2);
    }

    private void restartGame() {
        gameThread = null;
        pm = new PlayManager();
        restartButton.setVisible(false);
        gameOverHandled = false; // ✅ Réinitialisation ici
        this.requestFocusInWindow();
        launchGame();
    }
}







