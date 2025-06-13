package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseManager {
    // Informations de connexion à la base de données MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/tetris_db";
    private static final String USER = "root"; // Nom d'utilisateur par défaut XAMPP
    private static final String PASSWORD = ""; // Mot de passe vide (par défaut sur XAMPP)

    // Méthode pour établir la connexion
    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ✅ Charge le driver MySQL
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // ✅ Insère uniquement si le score est dans le top 10
    public void insertIfHighScore(String playerName, int score) {
        String countSql = "SELECT COUNT(*) AS total FROM scores";
        String minScoreSql = "SELECT id, score FROM scores ORDER BY score ASC, id ASC LIMIT 1";

        String insertSql = "INSERT INTO scores (nom_joueur, score) VALUES (?, ?)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet countRs = stmt.executeQuery(countSql)) {

            int total = 0;
            if (countRs.next()) {
                total = countRs.getInt("total");
            }

            if (total < 10) {
                // Moins de 10 scores → insérer directement
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, playerName);
                    pstmt.setInt(2, score);
                    pstmt.executeUpdate();
                }
            } else {
                // Il y a déjà 10 scores, vérifier le score minimum
                ResultSet minRs = stmt.executeQuery(minScoreSql);
                if (minRs.next()) {
                    int minScore = minRs.getInt("min_score");

                    if (score > minScore) {
                        // Nouveau score > plus bas score → insérer
                        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                            pstmt.setString(1, playerName);
                            pstmt.setInt(2, score);
                            pstmt.executeUpdate();
                        }

                        // Supprimer une des entrées ayant le plus bas score
                        String deleteSql = "DELETE FROM scores WHERE id = (SELECT id FROM scores WHERE score = ? ORDER BY id ASC LIMIT 1)";
     
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                            deleteStmt.setInt(1, minScore);
                            deleteStmt.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupérer les 10 meilleurs scores
    public ResultSet getBestScores() {
        String sql = "SELECT nom_joueur, score FROM scores ORDER BY score DESC LIMIT 10";
        ResultSet rs = null;
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}

