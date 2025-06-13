/*package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JPanel;

import Database.DatabaseManager;

public class ScorePanel extends JPanel {
    private ArrayList<String> scores = new ArrayList<>();

    public ScorePanel() {
        this.setBackground(Color.darkGray);
        this.setBounds(650, 50, 250, 400); // Position sur le côté droit de l'interface
        loadScores(); // Charger les scores au démarrage
    }

    // Charger les meilleurs scores depuis la base de données
    public void loadScores() {
        scores.clear(); // Nettoyer les anciens scores avant de mettre à jour
        DatabaseManager db = new DatabaseManager();
        
        try (Connection conn = db.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT nom_joueur, score FROM scores ORDER BY score DESC LIMIT 10")) {
            while (rs.next()) {
                scores.add(rs.getString("nom_joueur") + " - " + rs.getInt("score"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Dessiner l'affichage des scores dans l'interface du jeu
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.white);
        g.drawString("🏆 Meilleurs Scores 🏆", 50, 50);

        int y = 100;
        for (String score : scores) {
            g.drawString(score, 50, y);
            y += 30;
        }
    }
}

