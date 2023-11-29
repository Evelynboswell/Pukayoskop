import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignIn extends JFrame {
    private LoadingPane loadingPane;
    private static final int FRAME_WIDTH = 512;
    private static final int FRAME_HEIGHT = 384;
    private JLabel signUpLabel, namaLabel, passLabel,signUpLink,signUpText;
    private JTextField namaTF;
    private JPasswordField passTF;
    private JButton signInButton;
    private Color colorBg, colorFg, colorText;
    private LineBorder defaultBorder = new LineBorder(Color.gray, 1);
    private LineBorder activeBorder = new LineBorder(Color.decode("#000080"), 2);
    public SignIn() {
        // Frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Sign In Page");

        loadingPane = new LoadingPane();
        loadingPane.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to prevent memory leaks
        loadingPane.setVisible(false); // Initially hidden

        // Colors
        colorBg = Color.decode("#1F237F");
        colorFg = Color.decode("#FFBB32");
        colorText = Color.decode("#131557");

        // Container
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        // Labels
        signUpLabel = new JLabel("Welcome To Pukayoskop");
        signUpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        signUpLabel.setForeground(colorText);
        signUpLabel.setBounds(130, 30, 290, 22);

        namaLabel = new JLabel("Username");
        namaLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        namaLabel.setForeground(colorText);
        namaLabel.setBounds(130, 70, 100, 16);

        passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        passLabel.setForeground(colorText);
        passLabel.setBounds(130, 120, 100, 16);

        // TextFields
        namaTF = new JTextField();
        namaTF.setBackground(Color.white);
        namaTF.setBounds(130, 90, 250, 25);
        namaTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        // PasswordField
        passTF = new JPasswordField();
        passTF.setBackground(Color.white);
        passTF.setBounds(130, 145, 250, 25);
        passTF.setFont(new Font("Verdana", Font.PLAIN, 16));

        // Sign In Button
        signInButton = new JButton("Sign In");
        signInButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        signInButton.setBackground(colorBg);
        signInButton.setForeground(colorFg);
        signInButton.setBounds(190,220,120,40);
        signInButton.addActionListener((e) -> {
        });
        // Sign Up Option
        signUpText = new JLabel("Don't have an account? ");
        signUpText.setFont(new Font("Verdana", Font.PLAIN, 12));
        signUpText.setForeground(Color.BLACK);
        signUpText.setBounds(135, 300, 200, 20);

        signUpLink = new JLabel("Sign up here");
        signUpLink.setFont(new Font("Verdana", Font.BOLD, 12));
        signUpLink.setForeground(Color.BLUE);
        signUpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLink.setBounds(285, 300, 200, 20);
        contentPane.add(signUpLink);
        contentPane.add(signUpText);

        contentPane.add(signUpLabel);
        contentPane.add(namaLabel);
        contentPane.add(namaTF);
        contentPane.add(passLabel);
        contentPane.add(passTF);
        contentPane.add(signInButton);

        signInButton.addActionListener((e) -> {
            String username = namaTF.getText();
            String password = new String(passTF.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                loadingPane.setLoadingText("Loading, please wait...");
                loadingPane.setVisible(true); // Show loading pane

                boolean isValid = DatabaseManager.validateUser(username, password);

                if (isValid) {
                    dispose();
                    Homepage homepage = new Homepage();
                    homepage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(SignIn.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    namaTF.setText("");
                    passTF.setText("");
                }

                loadingPane.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter username and password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        signUpLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUp signUp = new SignUp();
                dispose();
                signUp.setVisible(true);
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set default border for text fields
        namaTF.setBorder(defaultBorder);
        passTF.setBorder(defaultBorder);

        // Change border color when mouse hovers over text fields
        namaTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                namaTF.setBorder(activeBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!namaTF.isFocusOwner()) {
                    namaTF.setBorder(defaultBorder);
                }
            }
        });

        passTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                passTF.setBorder(activeBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!passTF.isFocusOwner()) {
                    passTF.setBorder(defaultBorder);
                }
            }
        });

        // Change border color when text fields are focused or lose focus
        namaTF.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                namaTF.setBorder(activeBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (namaTF.getText().isEmpty()) {
                    namaTF.setBorder(defaultBorder);
                }
            }
        });

        passTF.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passTF.setBorder(activeBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passTF.getPassword()).isEmpty()) {
                    passTF.setBorder(defaultBorder);
                }
            }
        });
    }
    //Main
    public static void main(String[] args) {
        SignIn log = new SignIn();
        log.setVisible(true);
    }
}