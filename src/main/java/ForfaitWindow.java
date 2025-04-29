import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class ForfaitWindow extends JFrame {
    private int userId;
    private JPanel forfaitPanel;

    public ForfaitWindow(int userId) {
        this.userId = userId;

        setTitle("Mes Forfaits");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        forfaitPanel = new JPanel();
        forfaitPanel.setLayout(new BoxLayout(forfaitPanel, BoxLayout.Y_AXIS));

        loadForfaits();

        add(new JScrollPane(forfaitPanel), BorderLayout.CENTER);
    }

    private void loadForfaits() {
        String query = "SELECT id, start_time, end_time FROM booking WHERE user_id = ?";

        try (Connection connection = DbConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("id");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();

                System.out.println("Booking ID: " + bookingId + ", Start Time: " + startTime + ", End Time: " + endTime);

                JPanel forfaitPanelItem = new JPanel();
                forfaitPanelItem.setLayout(new BorderLayout());

                JButton forfaitButton = new JButton("Forfait " + bookingId);
                JLabel timerLabel = new JLabel();
                forfaitPanelItem.add(forfaitButton, BorderLayout.CENTER);
                forfaitPanelItem.add(timerLabel, BorderLayout.SOUTH);

                forfaitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleForfaitClick(bookingId, startTime, endTime, timerLabel);
                    }
                });

                forfaitPanel.add(forfaitPanelItem);
            }

            // Vérifiez si des forfaits ont été ajoutés
            if (forfaitPanel.getComponentCount() == 0) {
                forfaitPanel.add(new JLabel("Aucun forfait disponible."));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleForfaitClick(int bookingId, LocalDateTime startTime, LocalDateTime endTime, JLabel timerLabel) {
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime[] mutableEndTime = {endTime}; // Utiliser un tableau pour rendre endTime mutable

        if (now.isAfter(startTime) && now.isBefore(mutableEndTime[0])) {
            JOptionPane.showMessageDialog(this, "Session démarrée pour le forfait: " + bookingId);

            // Créer le bouton pour ajouter du temps
            JButton addTimeButton = new JButton("Ajouter 10 minutes");
            addTimeButton.setVisible(true); // Rendre le bouton visible uniquement pendant la session
            addTimeButton.addActionListener(e -> {
                try {
                    // Ajouter 10 minutes au timer
                    ForfaitManager forfaitManager = new ForfaitManager();
                    forfaitManager.addTimeToBooking(bookingId, 10);

                    // Mettre à jour l'heure de fin localement
                    mutableEndTime[0] = mutableEndTime[0].plusMinutes(10);

                    JOptionPane.showMessageDialog(this, "10 minutes ajoutées !");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de temps : " + ex.getMessage());
                }
            });

            // Ajouter le bouton à l'interface
            forfaitPanel.add(addTimeButton, BorderLayout.NORTH);
            forfaitPanel.revalidate();
            forfaitPanel.repaint();

            // Démarrer le timer
            startTimer(mutableEndTime[0], timerLabel, addTimeButton);
        } else {
            JOptionPane.showMessageDialog(this, "Ce n'est pas l'heure de votre session.");
        }
    }

    private void startTimer(LocalDateTime endTime, JLabel timerLabel, JButton addTimeButton) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LocalDateTime now = LocalDateTime.now();
                if (now.isBefore(endTime)) {
                    Duration duration = Duration.between(now, endTime);
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    long seconds = duration.getSeconds() % 60;
                    SwingUtilities.invokeLater(() -> timerLabel.setText(String.format("Temps restant: %02d:%02d:%02d", hours, minutes, seconds)));
                } else {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        timerLabel.setText("Forfait expiré!");
                        addTimeButton.setVisible(false); // Cacher le bouton lorsque le timer est terminé
                    });
                }
            }
        }, 0, 1000);
    }
}