import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/cyber_games_arras?serverVersion=8.0.40&charset=utf8mb4";
    private static final String USER = "localhost"; // Remplacez par votre nom d'utilisateur réel
    private static final String MOT_DE_PASSE = "Doliprane8%"; // Vérifiez votre mot de passe

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, MOT_DE_PASSE);
    }
}