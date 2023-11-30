import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Homepage extends JFrame {
    public Homepage() {
        setTitle("Cinema Ticket Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.decode("#1f237f"));
        sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));

        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(Box.createVerticalStrut(10));

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setForeground(Color.decode("#ffffff"));
        profileLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        profileLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        sidebarPanel.add(profileLabel);

        ImageIcon profileImageIcon = new ImageIcon("Icons\\profile.png");
        Image profileImage = profileImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledProfileImageIcon = new ImageIcon(profileImage);
        JLabel profileImageLabel = new JLabel(scaledProfileImageIcon);
        profileImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the image
        sidebarPanel.add(profileImageLabel);

        JLabel usernameLabel = new JLabel(DatabaseManager.getUsername());
        usernameLabel.setForeground(Color.decode("#ffffff"));
        usernameLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label
        sidebarPanel.add(usernameLabel);

        String originalImagePath = "Icons\\ticket.png";
        String alternateImagePath = "Icons\\tickethover.png";

        ImageIcon originalTicketIcon = new ImageIcon(new ImageIcon(originalImagePath).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        ImageIcon alternateTicketIcon = new ImageIcon(new ImageIcon(alternateImagePath).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        JLabel ticketImageLabel = new JLabel(originalTicketIcon);
        ticketImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the image
        sidebarPanel.add(Box.createVerticalStrut(40));
        sidebarPanel.add(ticketImageLabel);

        JLabel ticketLabel = new JLabel("Purchased Tickets");
        ticketLabel.setForeground(Color.decode("#ffffff"));
        ticketLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        ticketLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label

        ticketImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Bookings test = new Bookings();
                dispose();
                test.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ticketImageLabel.setIcon(alternateTicketIcon); // Change image to alternate on hover
                ticketImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovered
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ticketImageLabel.setIcon(originalTicketIcon); // Revert to original image on exit
                ticketImageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Change cursor back to default
            }
        });
        ticketLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Bookings test = new Bookings();
                dispose();
                test.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ticketLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovered
                ticketLabel.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ticketLabel.setForeground(Color.white);
                ticketLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Change cursor back to default
            }
        });

        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(ticketLabel);

        // Red Log Out Button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Verdana", Font.BOLD, 18));
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            dispose();
            SignIn loginFrame = new SignIn();
            loginFrame.setVisible(true);
        });

        sidebarPanel.add(Box.createVerticalStrut(50));
        sidebarPanel.add(logoutButton);

        /// Movie List Panel with Grid Layout (2 columns)
        JPanel moviePanel = new JPanel(new GridLayout(0, 2, 10, 20));
        moviePanel.setBackground(Color.decode("#ffffff"));
        moviePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        moviePanel.setBorder(new LineBorder(Color.black, 1));
        moviePanel.setPreferredSize(new Dimension(800, 1300));

        loadMoviesFromDatabase(moviePanel);

        JScrollPane movieScrollPane = new JScrollPane(moviePanel);
        movieScrollPane.setBackground(Color.decode("#ffffff"));
        movieScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add Components to Frame
        add(sidebarPanel, BorderLayout.WEST);
        add(movieScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    private void loadMoviesFromDatabase(JPanel moviePanel) {
        try {
            ResultSet resultSet = DatabaseManager.getMovies();
            while (resultSet.next()) {
                int filmId = resultSet.getInt("film_id");
                String title = resultSet.getString("title");

                JPanel movieEntry = new JPanel(new BorderLayout());
                movieEntry.setBackground(Color.decode("#ffffff"));
                movieEntry.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

                String imagePath = "Posters\\" + filmId + ".jpg";
                ImageIcon moviePosterIcon = new ImageIcon(imagePath);
                Image scaledMoviePoster = moviePosterIcon.getImage().getScaledInstance(300, 500, Image.SCALE_DEFAULT);
                ImageIcon scaledMoviePosterIcon = new ImageIcon(scaledMoviePoster);

                JLabel moviePosterLabel = new JLabel(scaledMoviePosterIcon);
                moviePosterLabel.setHorizontalAlignment(JLabel.CENTER);
                movieEntry.add(moviePosterLabel, BorderLayout.NORTH);

                JLabel movieTitleLabel = new JLabel(title);
                movieTitleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
                movieTitleLabel.setHorizontalAlignment(JLabel.CENTER);
                movieEntry.add(movieTitleLabel, BorderLayout.CENTER);

                JButton buyTicketButton = new JButton("Select Movie");
                buyTicketButton.setFont(new Font("Verdana", Font.BOLD, 18));
                buyTicketButton.setBackground(Color.decode("#1F237F"));
                buyTicketButton.setForeground(Color.decode("#FFBB32"));

                buyTicketButton.addActionListener(e -> {
                    System.out.println("Selected Film ID: " + filmId);
                    DatabaseManager.setSelectedFilmId(filmId);
                    this.dispose();
                    JadwalFilm jadwalFilm = new JadwalFilm(filmId);
                    jadwalFilm.setVisible(true);
                });

                JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonContainer.setBackground(Color.decode("#ffffff"));
                buttonContainer.add(buyTicketButton);
                movieEntry.add(buttonContainer, BorderLayout.SOUTH);
                moviePanel.add(movieEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while fetching movies. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Homepage homepage = new Homepage();
        homepage.setVisible(true);
    }

}