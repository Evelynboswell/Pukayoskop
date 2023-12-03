import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
public class Payment extends JFrame{
    private JButton blueBox, bayar;
    private JLabel posterLabel, judul, judulText, bioskop, bioskopText;
    private JLabel jadwal, jadwalText, jam, jamText, kursi, kursiText;
    private JLabel totalKursi, totalKursiNum, totalHarga, totalHargaText;
    private JLabel biayaAdmin, biayaAdminText, totalBayar, biayaFix;
    private ImageIcon poster;

    public Payment() {
        setTitle("Pembayaran Tiket Bioskop");
        setSize(1024, 768);
        setLocation(220, 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        int filmId = DatabaseManager.getSelectedFilmId();
        DatabaseManager.MovieDetails movieDetails = DatabaseManager.getMovieDetails(filmId);


        blueBox = new JButton();
        blueBox.setBounds(0, 0, 1024, 300);
        blueBox.setBackground(Color.decode("#1F237F"));
        blueBox.setEnabled(false);

        try {
            URL url = new URL(movieDetails.getImageUrl());
            Image image = ImageIO.read(url);
            poster = new ImageIcon(image);
        } catch (IOException e){
            e.printStackTrace();
        }
        poster.setImage(poster.getImage().getScaledInstance(170, 210, Image.SCALE_SMOOTH));
        posterLabel = new JLabel(poster);
        posterLabel.setBounds(30, 40, 200, 250);

        judul = new JLabel("JUDUL FILM");
        judul.setFont(new Font("Arial", Font.BOLD, 16));
        judul.setForeground(Color.decode("#A0A0A0"));
        judul.setBounds(250, 80, 100, 20);

        String filmTitle = movieDetails.getTitle();
        judulText = new JLabel(filmTitle);
        judulText.setFont(new Font("Arial", Font.BOLD, 18));
        judulText.setForeground(Color.WHITE);
        judulText.setBounds(250, 110, 150, 20);

        bioskop = new JLabel("BIOSKOP");
        bioskop.setFont(new Font("Arial", Font.BOLD, 16));
        bioskop.setForeground(Color.decode("#A0A0A0"));
        bioskop.setBounds(250, 160, 100, 20);

        bioskopText = new JLabel("PUKAYOSKOP YOGYAKARTA");
        bioskopText.setFont(new Font("Arial", Font.BOLD, 18));
        bioskopText.setForeground(Color.WHITE);
        bioskopText.setBounds(250, 190, 300, 20);

        jadwal = new JLabel("JADWAL");
        jadwal.setFont(new Font("Arial", Font.BOLD, 16));
        jadwal.setForeground(Color.decode("#A0A0A0"));
        jadwal.setBounds(50, 320, 150, 20);

        DatabaseManager.SelectedMovieDetails selectedMovieDetails = DatabaseManager.getSelectedMovieDetails();
        int showtimeId = DatabaseManager.getSelectedMovieDetails().getShowtimeId();
        String showtimeDate = DatabaseManager.getShowtimeDate(showtimeId);
        jadwalText = new JLabel(showtimeDate);
        jadwalText.setFont(new Font("Arial", Font.BOLD, 18));
        jadwalText.setBounds(50, 350, 300, 20);

        jam = new JLabel("JAM");
        jam.setFont(new Font("Arial", Font.BOLD, 16));
        jam.setForeground(Color.decode("#A0A0A0"));
        jam.setBounds(500, 320, 80, 20);

        String selectedTime = selectedMovieDetails.getSelectedTime();
        jamText = new JLabel(selectedTime);
        jamText.setFont(new Font("Arial", Font.BOLD, 18));
        jamText.setBounds(500, 350, 200, 20);

        kursi = new JLabel("TEMPAT DUDUK");
        kursi.setFont(new Font("Arial", Font.BOLD, 16));
        kursi.setForeground(Color.decode("#A0A0A0"));
        kursi.setBounds(50, 400, 150, 20);

        List<String> selectedSeats = DatabaseManager.getSelectedSeats(filmId,showtimeId);
        System.out.println(selectedSeats);
        String seatsString = String.join(", ", selectedSeats);
        kursiText = new JLabel(seatsString);
        kursiText.setFont(new Font("Arial", Font.BOLD, 18));
        kursiText.setBounds(50, 430, 200, 20);

        totalKursi = new JLabel("TOTAL KURSI");
        totalKursi.setFont(new Font("Arial", Font.BOLD, 16));
        totalKursi.setForeground(Color.decode("#A0A0A0"));
        totalKursi.setBounds(500, 400, 200, 20);

        int totalSeats = selectedSeats.size();
        System.out.println(totalSeats);
        totalKursiNum = new JLabel(String.valueOf(totalSeats));
        totalKursiNum.setFont(new Font("Arial", Font.BOLD, 18));
        totalKursiNum.setBounds(500, 430, 50, 20);

        totalHarga = new JLabel("TOTAL HARGA TIKET");
        totalHarga.setFont(new Font("Arial", Font.BOLD, 16));
        totalHarga.setForeground(Color.decode("#A0A0A0"));
        totalHarga.setBounds(50, 480, 200, 20);

        double totalSeatPrice = DatabaseManager.getTotalSeatPrice(filmId, showtimeId, selectedSeats);
        totalHargaText = new JLabel("Rp. " + String.valueOf(totalSeatPrice));
        totalHargaText.setFont(new Font("Arial", Font.BOLD, 18));
        totalHargaText.setBounds(50, 510, 150, 20);

        biayaAdmin = new JLabel("BIAYA LAYANAN");
        biayaAdmin.setFont(new Font("Arial", Font.BOLD, 16));
        biayaAdmin.setForeground(Color.decode("#A0A0A0"));
        biayaAdmin.setBounds(500, 480, 200, 20);

        double serviceFee = DatabaseManager.getServiceFee();
        biayaAdminText = new JLabel("Rp. " + String.valueOf(serviceFee));
        biayaAdminText.setFont(new Font("Arial", Font.BOLD, 18));
        biayaAdminText.setBounds(500, 510, 150, 20);

        totalBayar = new JLabel("TOTAL HARGA TIKET");
        totalBayar.setFont(new Font("Arial", Font.BOLD, 16));
        totalBayar.setForeground(Color.decode("#A0A0A0"));
        totalBayar.setBounds(50, 580, 200, 20);

        double totalPrice = DatabaseManager.calculateTotalPrice(totalSeatPrice);
        biayaFix = new JLabel("Rp. " + String.valueOf(totalPrice));
        biayaFix.setFont(new Font("Arial", Font.BOLD, 24));
        biayaFix.setBounds(500, 580, 150, 24);


        bayar = new JButton("BAYAR");
        bayar.setBounds(450, 630, 150, 50);
        bayar.setFont(new Font("ARIAL BLACK", Font.PLAIN, 18));
        bayar.setBackground(Color.decode("#1F237F"));
        bayar.setForeground(Color.decode("#FFBB32"));
        bayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to pay?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String user = DatabaseManager.getUsername();
                    int userId = DatabaseManager.getUserId(user);
                    DatabaseManager.insertTicket(userId, filmId, showtimeId, selectedSeats, totalPrice);
                } else if (confirm == JOptionPane.NO_OPTION) {
                    DatabaseManager.deleteSeats(filmId, showtimeId, selectedSeats);
                }
                dispose();
                Homepage homepage = new Homepage();
                homepage.setVisible(true);
            }
        });
        contentPane.add(blueBox);
        contentPane.add(posterLabel);
        contentPane.add(judul);
        contentPane.add(judulText);
        contentPane.add(bioskop);
        contentPane.add(bioskopText);
        contentPane.add(jadwal);
        contentPane.add(jadwalText);
        contentPane.add(jam);
        contentPane.add(jamText);
        contentPane.add(kursi);
        contentPane.add(kursiText);
        contentPane.add(totalKursi);
        contentPane.add(totalKursiNum);
        contentPane.add(totalHarga);
        contentPane.add(totalHargaText);
        contentPane.add(biayaAdmin);
        contentPane.add(biayaAdminText);
        contentPane.add(totalBayar);
        contentPane.add(biayaFix);
        contentPane.add(bayar);

        contentPane.setComponentZOrder(posterLabel, 0);
        contentPane.setComponentZOrder(judul, 0);
        contentPane.setComponentZOrder(judulText, 0);
        contentPane.setComponentZOrder(bioskop, 0);
        contentPane.setComponentZOrder(bioskopText, 0);
    }
}
