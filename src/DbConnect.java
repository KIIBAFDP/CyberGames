import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/cyber_games_arras";
    private static final String USER = "localhost";
    private static final String MOT_DE_PASSE = "Doliprane8%";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, MOT_DE_PASSE);
    }
}