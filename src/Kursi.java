import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class Kursi extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 768;
    private static final int FRAME_X_ORIGIN = 220;
    private static final int FRAME_Y_ORIGIN = 20;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;
    private JButton oke, cancel,A1,A2,A3,A4,B1,B2,B3,B4,C1,C2,C3,C4,D1,D2,D3,D4,E1,E2,E3,E4, button1, button2, button3, layar;
    private JLabel tersedia, terpilih, terjual;
    private Color colorOri, colorPick, colorFg, colorSold, colorFgSold;
    private List<String> selectedSeats;
    private int selectedSeatCounter;
    public static void main(String[] args) {
        Kursi frame = new Kursi();
        frame.setVisible(true);

    }
    public Kursi() {
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setTitle("Pilih Kursi");
        setLocation(FRAME_X_ORIGIN, FRAME_Y_ORIGIN);

        ImageIcon backIcon = new ImageIcon("Icons\\backbutton.png");
        ImageIcon backIconHover = new ImageIcon("Icons\\backbuttonhover.png");
        Image scaledBackIcon = backIcon.getImage().getScaledInstance(25,25, Image.SCALE_SMOOTH);
        Image scaledBackIconHover = backIconHover.getImage().getScaledInstance(25,25, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledBackIcon);
        ImageIcon scaledIconHover = new ImageIcon(scaledBackIconHover);
        JLabel backButton = new JLabel(scaledIcon);
        backButton.setBounds(20, 10, 25,25);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(backButton);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
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
        //Ini variable untuk nyimpan warna
        colorOri = Color.decode("#131557");
        colorPick = Color.decode("#2655FF");
        colorSold = Color.decode("#BBBECB");
        colorFg = Color.WHITE;
        colorFgSold = Color.decode("#828592");

        //Label tersedia, pilihan user, terjual
        tersedia = new JLabel("Tersedia");
        tersedia.setBounds(400, 10, 100, 25);
        tersedia.setFont(new Font("Arial", Font.BOLD, 14));
        tersedia.setForeground(colorFgSold);
        contentPane.add(tersedia);

        terpilih = new JLabel("Pilihan Anda");
        terpilih.setBounds(520, 10, 100, 25);
        terpilih.setFont(new Font("Arial", Font.BOLD, 14));
        terpilih.setForeground(colorFgSold);
        contentPane.add(terpilih);

        terjual = new JLabel("Terjual");
        terjual.setBounds(670, 10, 100, 25);
        terjual.setFont(new Font("Arial", Font.BOLD, 14));
        terjual.setForeground(colorFgSold);
        contentPane.add(terjual);

        button1 = new JButton();
        button1.setBounds(370, 10, 25, 25);
        button1.setBackground(colorOri);
        button1.setEnabled(false);
        contentPane.add(button1);

        button2 = new JButton();
        button2.setBounds(490, 10, 25, 25);
        button2.setBackground(colorPick);
        button2.setEnabled(false);
        contentPane.add(button2);

        button3 = new JButton();
        button3.setBounds(640, 10, 25, 25);
        button3.setBackground(colorSold);
        button3.setEnabled(false);
        contentPane.add(button3);

        //Kursi
        A1 = new JButton("A1");
        A1.setBounds(260, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        A1.setBackground(colorOri);
        A1.setForeground(colorFg);
        A1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(A1);

        A2 = new JButton("A2");
        A2.setBounds(350, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        A2.setBackground(colorOri);
        A2.setForeground(colorFg);
        A2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(A2);

        A3 = new JButton("A3");
        A3.setBounds(600, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        A3.setBackground(colorOri);
        A3.setForeground(colorFg);
        A3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(A3);

        A4 = new JButton("A4");
        A4.setBounds(690, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
        A4.setBackground(colorOri);
        A4.setForeground(colorFg);
        A4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(A4);

        B1 = new JButton("B1");
        B1.setBounds(260, 140, BUTTON_WIDTH, BUTTON_HEIGHT);
        B1.setBackground(colorOri);
        B1.setForeground(colorFg);
        B1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(B1);

        B2 = new JButton("B2");
        B2.setBounds(350, 140, BUTTON_WIDTH, BUTTON_HEIGHT);
        B2.setBackground(colorOri);
        B2.setForeground(colorFg);
        B2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(B2);

        B3 = new JButton("B3");
        B3.setBounds(600, 140, BUTTON_WIDTH, BUTTON_HEIGHT);
        B3.setBackground(colorOri);
        B3.setForeground(colorFg);
        B3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(B3);

        B4 = new JButton("B4");
        B4.setBounds(690, 140, BUTTON_WIDTH, BUTTON_HEIGHT);
        B4.setBackground(colorOri);
        B4.setForeground(colorFg);
        B4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(B4);

        C1 = new JButton("C1");
        C1.setBounds(260, 230, BUTTON_WIDTH, BUTTON_HEIGHT);
        C1.setBackground(colorOri);
        C1.setForeground(colorFg);
        C1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(C1);

        C2 = new JButton("C2");
        C2.setBounds(350, 230, BUTTON_WIDTH, BUTTON_HEIGHT);
        C2.setBackground(colorOri);
        C2.setForeground(colorFg);
        C2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(C2);

        C3 = new JButton("C3");
        C3.setBounds(600, 230, BUTTON_WIDTH, BUTTON_HEIGHT);
        C3.setBackground(colorOri);
        C3.setForeground(colorFg);
        C3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(C3);

        C4 = new JButton("C4");
        C4.setBounds(690, 230, BUTTON_WIDTH, BUTTON_HEIGHT);
        C4.setBackground(colorOri);
        C4.setForeground(colorFg);
        C4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(C4);

        D1 = new JButton("D1");
        D1.setBounds(260, 320, BUTTON_WIDTH, BUTTON_HEIGHT);
        D1.setBackground(colorOri);
        D1.setForeground(colorFg);
        D1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(D1);

        D2 = new JButton("D2");
        D2.setBounds(350, 320, BUTTON_WIDTH, BUTTON_HEIGHT);
        D2.setBackground(colorOri);
        D2.setForeground(colorFg);
        D2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(D2);

        D3 = new JButton("D3");
        D3.setBounds(600, 320, BUTTON_WIDTH, BUTTON_HEIGHT);
        D3.setBackground(colorOri);
        D3.setForeground(colorFg);
        D3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(D3);

        D4 = new JButton("D4");
        D4.setBounds(690, 320, BUTTON_WIDTH, BUTTON_HEIGHT);
        D4.setBackground(colorOri);
        D4.setForeground(colorFg);
        D4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(D4);

        E1 = new JButton("E1");
        E1.setBounds(260, 410, BUTTON_WIDTH, BUTTON_HEIGHT);
        E1.setBackground(colorOri);
        E1.setForeground(colorFg);
        E1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(E1);

        E2 = new JButton("E2");
        E2.setBounds(350, 410, BUTTON_WIDTH, BUTTON_HEIGHT);
        E2.setBackground(colorOri);
        E2.setForeground(colorFg);
        E2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(E2);

        E3 = new JButton("E3");
        E3.setBounds(600, 410, BUTTON_WIDTH, BUTTON_HEIGHT);
        E3.setBackground(colorOri);
        E3.setForeground(colorFg);
        E3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(E3);

        E4 = new JButton("E4");
        E4.setBounds(690, 410, BUTTON_WIDTH, BUTTON_HEIGHT);
        E4.setBackground(colorOri);
        E4.setForeground(colorFg);
        E4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
        contentPane.add(E4);

        //Layar bioskop
        layar = new JButton("Layar Bioskop");
        layar.setBounds(260, 520, 510, 35);
        layar.setBackground(colorSold);
        layar.setForeground(colorFgSold);
        layar.setEnabled(false);
        layar.setFont(new Font("Arial", Font.BOLD, 16));
        contentPane.add(layar);

        //OK dan CANCEL
        oke = new JButton("OKE");
        oke.setBounds(350, 630, 150, 50);
        oke.setFont(new Font("ARIAL BLACK", Font.PLAIN, 18));
        oke.setBackground(Color.decode("#1F237F"));
        oke.setForeground(Color.decode("#FFBB32"));
        contentPane.add(oke);

        cancel = new JButton("CANCEL");
        cancel.setBounds(530, 630,150, 50);
        cancel.setFont(new Font("ARIAL BLACK", Font.PLAIN, 18));
        cancel.setBackground(Color.decode("#1F237F"));
        cancel.setForeground(Color.decode("#FFBB32"));
        contentPane.add(cancel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        A1.addActionListener(this);
        A2.addActionListener(this);
        A3.addActionListener(this);
        A4.addActionListener(this);
        B1.addActionListener(this);
        B2.addActionListener(this);
        B3.addActionListener(this);
        B4.addActionListener(this);
        C1.addActionListener(this);
        C2.addActionListener(this);
        C3.addActionListener(this);
        C4.addActionListener(this);
        D1.addActionListener(this);
        D2.addActionListener(this);
        D3.addActionListener(this);
        D4.addActionListener(this);
        E1.addActionListener(this);
        E2.addActionListener(this);
        E3.addActionListener(this);
        E4.addActionListener(this);

        selectedSeats = new ArrayList<>();
        oke.addActionListener(this);
        int filmId = DatabaseManager.getSelectedFilmId();
        int showtimeId = DatabaseManager.getSelectedMovieDetails().getShowtimeId();
        int n = selectedSeatCounter;
        List<String> bookedSeats = DatabaseManager.getBookedSeats(filmId, showtimeId);
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String buttonText = button.getText();
                if (buttonText.matches("[A-E][1-4]") && bookedSeats.contains(buttonText)) {
                    button.setEnabled(false);
                    button.setBackground(Color.decode("#BBBECB"));
                }
            }
        }
        selectedSeatCounter = 0;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.getText().matches("[A-E][1-4]")) {
            String seatName = source.getText();

            if (source.getBackground() == colorOri) {
                source.setBackground(colorPick);
                source.setForeground(colorFg);
                selectedSeats.add(seatName);
                selectedSeatCounter++;
            } else if (source.getBackground() == colorPick) {
                source.setBackground(colorOri);
                source.setForeground(colorFg);
                selectedSeats.remove(seatName);
                selectedSeatCounter--;
            }
            System.out.println("Selected seats: " + selectedSeatCounter);
        }
        else if (source == oke) {
            int filmId = DatabaseManager.getSelectedFilmId();
            int showtimeId = DatabaseManager.getSelectedMovieDetails().getShowtimeId();
            for (String seat : selectedSeats) {
                Component[] components = getContentPane().getComponents();
                for (Component component : components) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        if (button.getText().equals(seat) && !button.getBackground().equals(Color.decode("#BBBECB"))) {
                            DatabaseManager.insertSeat(filmId, showtimeId, seat);
                        }
                    }
                }
            }
            Payment payment = new Payment(selectedSeatCounter);
            this.dispose();
            payment.setVisible(true);
        }
    }
}
