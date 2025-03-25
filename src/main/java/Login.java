import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Login {
    private int userId; // Stocke l'ID de l'utilisateur connecté

    public int getUserId() {
        return userId;
    }

    public String verifyLogin(String username, String password) throws SQLException {
        // Utilisez ici "pseudo" car c'est le nom réel dans la base de données
        String query = "SELECT id, password FROM user WHERE pseudo = ?";

        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("password");
                this.userId = resultSet.getInt("id");

                if (storedHashedPassword != null && storedHashedPassword.startsWith("$2y$")) {
                    storedHashedPassword = storedHashedPassword.replace("$2y$", "$2a$");
                }

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    return "Connexion Réussie";
                } else {
                    return "Identifiant ou mot de passe incorrect";
                }
            } else {
                return "Identifiant ou mot de passe incorrect";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erreur lors de la connexion à la BDD";
        }
    }
}