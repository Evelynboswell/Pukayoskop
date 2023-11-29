import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class JadwalFilm extends JFrame {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 768;
    private JButton buttonBeliTiket, buttonHari, buttonJam2D;
    private JLabel judulFilm, jadwal, genre, durasi, rating, reguler, harga, poster;
    private JLabel textGenre, textDurasi, textRating, jam, outOfTen;
    private JTextArea descriptionArea;
    private ImageIcon posterFilm;
    private JButton prev1 = null;
    private JButton prev2 = null;
    private String pilihHari = null;
    private String pilihJam = null;

    public JadwalFilm(int selectedFilmId) {
        // Set image
        posterFilm = new ImageIcon();
        poster = new JLabel();

        // Fetch movie details using SelectedMovie class
        DatabaseManager.MovieDetails movieDetails = DatabaseManager.getMovieDetails(selectedFilmId);
        DatabaseManager.MovieShowtime movieShowtime = DatabaseManager.getMovieShowtime(selectedFilmId);
        // Frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Jadwal Film");

        // Container
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        // Label
        judulFilm = new JLabel();
        judulFilm.setFont(new Font("Serif", Font.BOLD, 24));
        judulFilm.setBounds(300, 50, 300, 50);

        genre = new JLabel("Genre");
        genre.setFont(new Font("Serif", Font.PLAIN, 17));
        genre.setForeground(Color.decode("#A0A0A0"));
        genre.setBounds(300, 105, 80, 20);

        durasi = new JLabel("Durasi");
        durasi.setFont(new Font("Serif", Font.PLAIN, 17));
        durasi.setForeground(Color.decode("#A0A0A0"));
        durasi.setBounds(300, 135, 80, 20);

        jam = new JLabel("jam");
        jam.setFont(new Font("Serif", Font.PLAIN, 17));
        jam.setForeground(Color.decode("#A0A0A0"));
        jam.setBounds(410, 135, 80, 20);

        rating = new JLabel("Rating");
        rating.setFont(new Font("Serif", Font.PLAIN, 17));
        rating.setForeground(Color.decode("#A0A0A0"));
        rating.setBounds(300, 165, 80, 20);

        outOfTen = new JLabel("/10");
        outOfTen.setFont(new Font("Serif", Font.PLAIN, 17));
        outOfTen.setForeground(Color.decode("#A0A0A0"));
        outOfTen.setBounds(410, 165, 80, 20);

        jadwal = new JLabel("Jadwal");
        jadwal.setFont(new Font("Serif", Font.BOLD, 20));
        jadwal.setBounds(300, 250, 100, 30);

        reguler = new JLabel("Jam Tayang");
        reguler.setFont(new Font("Serif", Font.BOLD, 20));
        reguler.setBounds(300, 370, 150, 30);
        reguler.setForeground(Color.black);

        harga = new JLabel("Rp. 30.000");
        harga.setFont(new Font("Arial", Font.PLAIN, 15));
        harga.setBounds(800, 400, 100, 20);
        harga.setForeground(Color.decode("#A0A0A0"));

        textGenre = new JLabel();
        textGenre.setFont(new Font("Serif", Font.PLAIN, 17));
        textGenre.setBounds(385, 105, 100, 20);

        textDurasi = new JLabel();
        textDurasi.setFont(new Font("Serif", Font.PLAIN, 17));
        textDurasi.setBounds(385, 135, 150, 20);

        textRating = new JLabel();
        textRating.setFont(new Font("Serif", Font.PLAIN, 17));
        textRating.setBounds(385, 165, 100, 20);

        // Text area
        descriptionArea = new JTextArea(3, 5);
        descriptionArea.setBounds(610, 105, 350, 120);
        descriptionArea.setFont(new Font("Serif", Font.PLAIN, 17));
        descriptionArea.setEditable(false);

        // Update GUI components with fetched movie details
        judulFilm.setText(movieDetails.getTitle());
        textGenre.setText(movieDetails.getGenre());
        textDurasi.setText(movieDetails.getDuration());
        textRating.setText(movieDetails.getRating());

        // Insert line breaks in the description text
        String originalDescription = movieDetails.getDescription();
        String formattedDescription = formatDescription(originalDescription);
        descriptionArea.setText(formattedDescription);
        // Button Hari


        String[] days = movieShowtime.getShowtimeDates();
        int x = 300;
        for (String day : days) {
            if(day != null) {
                buttonHari = new JButton(day);
                buttonHari.setBounds(x, 290, 85, 40);
                buttonHari.setBackground(Color.WHITE);
                buttonHari.setFont(new Font("ARIAL BLACK", Font.PLAIN, 12));
                buttonHari.addActionListener((ActionEvent e) -> {
                    pilihHari = day;
                    clickedBeliButton();
                });
                buttonColorChange1(buttonHari);
                contentPane.add(buttonHari);
                x += 100;
            }

        }
        // Button Jam Tayang
        String[] jam1 = movieShowtime.getStartTimes();
        int y = 300;
        for (String j : jam1) {
            if(j != null) {
                buttonJam2D = new JButton(j);
                buttonJam2D.setBounds(y, 410, 95, 30);
                buttonJam2D.setFont(new Font("Arial", Font.BOLD, 12));
                buttonJam2D.setBackground(Color.WHITE);
                buttonJam2D.addActionListener((ActionEvent e) -> {
                    pilihJam = j;
                    clickedBeliButton();
                });
                buttonColorChange2(buttonJam2D);
                contentPane.add(buttonJam2D);
                y += 110;
            }

        }
        // Button Beli Tiket
        buttonBeliTiket = new JButton("BELI TIKET");
        buttonBeliTiket.setBounds(450, 630, 170, 50);
        buttonBeliTiket.setFont(new Font("ARIAL BLACK", Font.PLAIN, 18));

        buttonBeliTiket.setBackground(Color.decode("#1F237F"));
        buttonBeliTiket.setForeground(Color.decode("#FFBB32"));
        buttonBeliTiket.setEnabled(false);
        buttonBeliTiket.addActionListener((ActionEvent e) -> {
            if (e.getSource() == buttonBeliTiket) {
                String selectedDay = pilihHari;
                String selectedTime = pilihJam;
                // Create an instance of SelectedMovieDetails and pass the selected values
                DatabaseManager.SelectedMovieDetails movieInfo = new DatabaseManager.SelectedMovieDetails(selectedDay, selectedTime);

                // Display or utilize the selected values using the new class
                movieInfo.displaySelectedDetails();

                // Open the seat selection window or perform further actions
                this.dispose();
                Kursi kursi = new Kursi();
                kursi.setVisible(true);
            }
        });

        // Back button
        ImageIcon backIcon = new ImageIcon("Icons\\backbutton.png");
        ImageIcon backIconHover = new ImageIcon("Icons\\backbuttonhover.png");

        int iconWidth = 25;
        int iconHeight = 25;

        Image scaledBackIcon = backIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        Image scaledBackIconHover = backIconHover.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledBackIcon);
        ImageIcon scaledIconHover = new ImageIcon(scaledBackIconHover);

        JLabel backButton = new JLabel(scaledIcon);
        backButton.setBounds(20, 10, iconWidth, iconHeight);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create an instance of SignIn when back button is clicked
                dispose(); // Close the current sign-up window
                Homepage hp = new Homepage();
                hp.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setIcon(scaledIconHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(scaledIcon);
            }
        });

        // Load and set poster image using a separate thread
        new Thread(() -> {
            try {
                URL url = new URL(movieDetails.getImageUrl());
                Image image = ImageIO.read(url);
                posterFilm.setImage(image.getScaledInstance(250, 310, Image.SCALE_SMOOTH));

                // Update the poster image on the EDT (Event Dispatch Thread)
                SwingUtilities.invokeLater(() -> {
                    poster.setIcon(posterFilm);
                    poster.setBounds(20, 50, 270, 330);
                });
            } catch (IOException e) {
                e.printStackTrace();
                // Handle image loading error (display default image or skip entry)
            }
        }).start();

        contentPane.add(judulFilm);
        contentPane.add(genre);
        contentPane.add(durasi);
        contentPane.add(jam);
        contentPane.add(rating);
        contentPane.add(outOfTen);
        contentPane.add(descriptionArea);
        contentPane.add(jadwal);
        contentPane.add(buttonBeliTiket);
        contentPane.add(reguler);
        contentPane.add(harga);
        contentPane.add(poster);
        contentPane.add(textGenre);
        contentPane.add(textDurasi);
        contentPane.add(textRating);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Method untuk mengganti warna dari buttonHari
    public void buttonColorChange1(JButton button) {
        button.addActionListener((ActionEvent e) -> {
            JButton button1 = (JButton) e.getSource();
            if (prev1 != null) {
                prev1.setBackground((Color) prev1.getClientProperty(e));
                prev1.setForeground(Color.BLACK);
            }
            Color originalTaste = button1.getBackground();
            button1.putClientProperty(e, originalTaste);
            button1.setBackground(Color.decode("#1F237F"));
            button1.setForeground(Color.WHITE);
            prev1 = button1;
        });
    }

    // Method untuk mengganti warna dari buttonJam
    public void buttonColorChange2(JButton button) {
        button.addActionListener((ActionEvent f) -> {
            JButton clickedButton = (JButton) f.getSource();
            if (prev2 != null && prev2 != clickedButton) {
                prev2.setBackground((Color) prev2.getClientProperty(f));
                prev2.setForeground(Color.BLACK);
            }

            Color originalColor = clickedButton.getBackground();
            clickedButton.putClientProperty(f, originalColor);
            clickedButton.setBackground(Color.decode("#1F237F"));
            clickedButton.setForeground(Color.WHITE);
            prev2 = clickedButton;
            clickedBeliButton(); // Check if both day and time are selected
        });
    }

    // Method untuk membuat button Beli Tiket dapat diklik
    public void clickedBeliButton() {
        if (pilihHari != null && pilihJam != null) {
            buttonBeliTiket.setEnabled(true);
        } else {
            buttonBeliTiket.setEnabled(false);
        }
    }

    // Method to insert line breaks in the description text
    private String formatDescription(String originalDescription) {
        int maxCharactersPerLine = 40; // Adjust this based on your preference

        StringBuilder formattedDescription = new StringBuilder();
        String[] words = originalDescription.split("\\s+");

        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() > maxCharactersPerLine) {
                formattedDescription.append("\n");
                currentLineLength = 0;
            }
            formattedDescription.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }

        return formattedDescription.toString().trim();
    }

    public static void main(String[] args) {
        JadwalFilm jad = new JadwalFilm(3); //Test out
        jad.setVisible(true);
    }
}
