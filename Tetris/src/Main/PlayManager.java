package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Database.DatabaseManager;
import Mino.*;

public class PlayManager {

    final int WIDTH = 360;
    final int height = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_BOX_X;
    final int NEXT_BOX_Y;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // Image de fond
    Image backgroundImage;

    // Autres
    public static int dropInterval = 60;
    boolean gameOver;
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    int level = 1;
    int lines;
    int score;

    private DatabaseManager db;

    public PlayManager() {
        left_x = (GamePanel.WIDTH - WIDTH) / 2;
        right_x = left_x + WIDTH;
        top_y = 20;
        bottom_y = top_y + height;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;
        NEXT_BOX_X = right_x + 40;
        NEXT_BOX_Y = bottom_y - 180;

        staticBlocks = new ArrayList<>();
        dropInterval = 60;
        gameOver = false;
        effectCounterOn = false;
        effectCounter = 0;
        effectY = new ArrayList<>();
        level = 1;
        lines = 0;
        score = 0;

        // Charger l'image de fond
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tet.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        centerNextMino();

        db = new DatabaseManager();
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0: mino = new Mino_L1(); break;
            case 1: mino = new Mino_L2(); break;
            case 2: mino = new Mino_Square(); break;
            case 3: mino = new Mino_Bar(); break;
            case 4: mino = new Mino_T(); break;
            case 5: mino = new Mino_Z1(); break;
            case 6: mino = new Mino_Z2(); break;
        }
        return mino;
    }

    private void centerNextMino() {
        int boxCenterX = NEXT_BOX_X + 150 / 2;
        int centerX = boxCenterX - 20;
        int centerY = NEXT_BOX_Y + 50;
        nextMino.setXY(centerX, centerY);
    }

    public void update() {
        if (!currentMino.active) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            boolean isGameOver = false;
            for (int i = 0; i < 4; i++) {
                if (currentMino.b[i].y <= MINO_START_Y) {
                    isGameOver = true;
                    break;
                }
            }
            
            if (isGameOver) {
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.se.play(2, false);

                SwingUtilities.invokeLater(() -> {
                    String playerName = JOptionPane.showInputDialog(null, "Entrez votre nom :", "Game Over", JOptionPane.PLAIN_MESSAGE);
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        db.insertIfHighScore(playerName, this.score);

                    }
                    GamePanel.restartButton.setVisible(true);
                });

                return;
            }

            currentMino.deactivating = false;
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            centerNextMino();
            checkDelete();

        } else {
            currentMino.update();
        }
    }

    private void checkDelete() {
        int y = top_y;
        int blockCount = 0;
        int linesThisTurn = 0;

        while (y < bottom_y) {
            blockCount = 0;

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            if (blockCount == 12) {
                effectCounterOn = true;
                effectY.add(y);

                for (int i = staticBlocks.size() - 1; i >= 0; i--) {
                    if (staticBlocks.get(i).y == y) {
                        staticBlocks.remove(i);
                    }
                }

                for (int i = 0; i < staticBlocks.size(); i++) {
                    if (staticBlocks.get(i).y < y) {
                        staticBlocks.get(i).y += Block.SIZE;
                    }
                }

                linesThisTurn++;
                continue;
            }

            y += Block.SIZE;
        }

        switch (linesThisTurn) {
            case 1: score += 10 * level; break;
            case 2: score += 20 * level; break;
            case 3: score += 30 * level; break;
            case 4: score += 40 * level; break;
        }

        if (linesThisTurn > 0) {
            GamePanel.se.play(1, false);
        }

        lines += linesThisTurn;
        int newLevel = (lines / 10) + 1;

        if (newLevel > level) {
            if (dropInterval > 10)
                dropInterval -= 10;
            else if (dropInterval > 1)
                dropInterval -= 1;

            level = newLevel;
        }
    }

    public void draw(Graphics2D q2) {
        q2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font modernFont = new Font("Arial", Font.BOLD, 24);
        q2.setFont(modernFont);
        q2.setColor(Color.white);

        if (backgroundImage != null) {
            q2.drawImage(backgroundImage, left_x, top_y, WIDTH, height, null);
        }

        q2.setStroke(new BasicStroke(4f));
        q2.drawRoundRect(left_x - 4, top_y - 4, WIDTH + 8, height + 8, 20, 20);
        q2.drawRoundRect(NEXT_BOX_X, NEXT_BOX_Y, 215, 170, 20, 20);

        q2.setFont(new Font("Arial", Font.BOLD, 32));
        q2.drawString("NEXT", NEXT_BOX_X + 70, NEXT_BOX_Y + 150);

        q2.setFont(modernFont);

        int blockWidth = 220;
        int blockHeight = 70;
        int spacing = 15;
        int infoX = right_x + 35;
        int infoY = top_y + 50;

        q2.drawRoundRect(infoX, infoY, blockWidth, blockHeight, 20, 20);
        q2.drawString("LEVEL: " + level, infoX + 20, infoY + 45);
        infoY += blockHeight + spacing;

        q2.drawRoundRect(infoX, infoY, blockWidth, blockHeight, 20, 20);
        q2.drawString("LINES: " + lines, infoX + 20, infoY + 45);
        infoY += blockHeight + spacing;

        q2.drawRoundRect(infoX, infoY, blockWidth, blockHeight, 20, 20);
        q2.drawString("SCORE: " + score, infoX + 20, infoY + 45);

        if (currentMino != null) currentMino.draw(q2);
        nextMino.draw(q2);

        for (Block b : staticBlocks) b.draw(q2);

        if (effectCounterOn) {
            effectCounter++;
            q2.setColor(Color.red);
            for (int y : effectY) {
                q2.fillRect(left_x, y, WIDTH, Block.SIZE);
            }
            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
            q2.setColor(Color.white);
        }

        q2.setColor(Color.yellow);
        q2.setFont(q2.getFont().deriveFont(50f));

        if (gameOver) {
            int x1 = left_x + 30;
            int y1 = top_y + height / 2;
            q2.drawString("GAME OVER", x1, y1);
        } else if (KeyHandler.pausePressed) {
            int textX = left_x + 73;
            int textY = top_y + height / 2;
            q2.drawString("PAUSED", textX, textY);
        }
    }
}
