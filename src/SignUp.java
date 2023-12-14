import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUp extends JFrame {
    private static final int FRAME_WIDTH = 512;
    private static final int FRAME_HEIGHT = 384;
    private JLabel signUpLabel, namaLabel, noHpLabel, userLabel, passLabel;
    private JTextField namaTF, noHpTF, userTF;
    private JPasswordField passTF;
    private JButton signUpButton;
    private Color colorBg, colorFg, colorText;
    private LineBorder defaultBorder = new LineBorder(Color.gray, 1);
    private LineBorder activeBorder = new LineBorder(Color.decode("#000080"), 2);
    private String username;
    public SignUp() {
        // Frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Sign Up Page");

        // Colors
        colorBg = Color.decode("#1F237F");
        colorFg = Color.decode("#FFBB32");
        colorText = Color.decode("#131557");

        // Container
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

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
        backButton.setBounds(10, 10, iconWidth, iconHeight);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Menutup window SignUp dan menampilkan window SignIn ketika 'back button' diklik 
                dispose();
                SignIn signIn = new SignIn();
                signIn.setVisible(true);
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

        // Labels
        signUpLabel = new JLabel("Welcome To Pukayoskop");
        signUpLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        signUpLabel.setForeground(colorText);
        signUpLabel.setBounds(130, 30, 290, 22);

        namaLabel = new JLabel("Nama Lengkap");
        namaLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        namaLabel.setForeground(colorText);
        namaLabel.setBounds(130, 69, 150, 16);

        noHpLabel = new JLabel("No. HP");
        noHpLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        noHpLabel.setForeground(colorText);
        noHpLabel.setBounds(130, 115, 100, 16);

        userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        userLabel.setForeground(colorText);
        userLabel.setBounds(130, 165, 100, 16);

        passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        passLabel.setForeground(colorText);
        passLabel.setBounds(130, 210, 100, 16);

        // TextField
        namaTF = new JTextField();
        namaTF.setBackground(Color.white);
        namaTF.setBounds(130, 90, 250, 25);
        namaTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        noHpTF = new JTextField();
        noHpTF.setBackground(Color.white);
        noHpTF.setBounds(130, 135, 250, 25);
        noHpTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        userTF = new JTextField();
        userTF.setBackground(Color.white);
        userTF.setBounds(130, 182, 250, 25);
        userTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        // PasswordField
        passTF = new JPasswordField();
        passTF.setBackground(Color.white);
        passTF.setBounds(130, 230, 250, 25);
        passTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        // Button
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        signUpButton.setBackground(colorBg);
        signUpButton.setForeground(colorFg);
        signUpButton.setBounds(195,285,120,40);
        signUpButton.addActionListener((e) -> {
            String vibeCheck = getText();
            if (vibeCheck.equals("filled")) {
                clickedButton(vibeCheck);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        contentPane.add(signUpLabel);
        contentPane.add(namaLabel);
        contentPane.add(namaTF);
        contentPane.add(noHpLabel);
        contentPane.add(noHpTF);
        contentPane.add(userLabel);
        contentPane.add(userTF);
        contentPane.add(passLabel);
        contentPane.add(passTF);
        contentPane.add(signUpButton);

        // Memberikan border tipe default untuk text field 
        namaTF.setBorder(defaultBorder);
        noHpTF.setBorder(defaultBorder);
        userTF.setBorder(defaultBorder);
        passTF.setBorder(defaultBorder);

        // Mengganti warna border ketika mouse berada di atas text field
        MouseAdapter borderMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JTextField textField = (JTextField) e.getSource();
                textField.setBorder(activeBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JTextField textField = (JTextField) e.getSource();
                if (!textField.isFocusOwner()) {
                    textField.setBorder(defaultBorder);
                }
            }
        };

        namaTF.addMouseListener(borderMouseAdapter);
        noHpTF.addMouseListener(borderMouseAdapter);
        userTF.addMouseListener(borderMouseAdapter);
        passTF.addMouseListener(borderMouseAdapter);

        // Mengganti warna border ketika text field sedang atau tidak difokuskan
        FocusAdapter borderFocusAdapter = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textField = (JTextField) e.getSource();
                textField.setBorder(activeBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField = (JTextField) e.getSource();
                if (textField.getText().isEmpty()) {
                    textField.setBorder(defaultBorder);
                }
            }
        };

        namaTF.addFocusListener(borderFocusAdapter);
        noHpTF.addFocusListener(borderFocusAdapter);
        userTF.addFocusListener(borderFocusAdapter);
        passTF.addFocusListener(borderFocusAdapter);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public String getText() {
        String nama = namaTF.getText();
        String noHp = noHpTF.getText();
        String user = userTF.getText();
        String pass = passTF.getText();
        String vibeCheck = "";
        if(!nama.equals("") && !noHp.equals("") && !user.equals("") &&!pass.equals("")) {
            return vibeCheck = "filled";
        }
        return vibeCheck;
    }
    public void clickedButton(String vibeCheck) {
        if (vibeCheck.equals("filled")) {
            boolean inserted = DatabaseManager.insertUser(namaTF.getText(), userTF.getText(), passTF.getText(), noHpTF.getText());
            if (inserted) {
                JOptionPane.showMessageDialog(this, "Sign-up successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                SignIn sign = new SignIn();
                sign.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //Main
    public static void main(String[] args) {
        SignUp log = new SignUp();
        log.setVisible(true);
    }
}
