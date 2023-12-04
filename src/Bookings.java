import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bookings extends JFrame {
    private LoadingPane loadingPane;
    private String usr = DatabaseManager.getUsername();
    private int id = DatabaseManager.getUserId(usr);
    Bookings(){
        setTitle("Cinema Ticket Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        setVisible(true);

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

        String originalImagePath = "Icons\\home.png";
        String alternateImagePath = "Icons\\homehover.png";

        ImageIcon originalTicketIcon = new ImageIcon(new ImageIcon(originalImagePath).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        ImageIcon alternateTicketIcon = new ImageIcon(new ImageIcon(alternateImagePath).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        JLabel ticketImageLabel = new JLabel(originalTicketIcon);
        ticketImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the image
        sidebarPanel.add(Box.createVerticalStrut(40));
        sidebarPanel.add(ticketImageLabel);

        JLabel ticketLabel = new JLabel("Home");
        ticketLabel.setForeground(Color.decode("#ffffff"));
        ticketLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        ticketLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the label

        ticketImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Homepage homepage = new Homepage();
                dispose();
                homepage.setVisible(true);
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
                Homepage homepage = new Homepage();
                dispose();
                homepage.setVisible(true);
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
        add(sidebarPanel, BorderLayout.WEST);


        JPanel ticketPanel = new JPanel();
        ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
        ticketPanel.setBackground(Color.decode("#ffffff"));
        ticketPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ticketPanel.setBorder(new LineBorder(Color.black, 1));
        ticketPanel.setPreferredSize(new Dimension(800, 1000));

        JLabel messageLabel = new JLabel("Select A Ticket To Download");
        messageLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        ticketPanel.add(messageLabel);
        JScrollPane ticketScrollPane = new JScrollPane(ticketPanel);
        ticketScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(ticketScrollPane, BorderLayout.CENTER);
        getTicketDetails(ticketPanel);
    }
    public void getTicketDetails(JPanel ticketPanel) {
        List<Integer> usedTicketIds = new ArrayList<>();
        try {
            ResultSet resultSet = DatabaseManager.getTicketsDetails(id);

            boolean hasTickets = false;

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                if (userId != DatabaseManager.getUserId(usr)) {
                    continue;
                }

                int ticketId = resultSet.getInt("ticket_id");
                if (usedTicketIds.contains(ticketId)) {
                    continue;
                }
                usedTicketIds.add(ticketId);
                System.out.println(usedTicketIds);
                hasTickets = true;

                String movieTitle = resultSet.getString("title");
                String showDate = resultSet.getString("showtime_date");
                String formattedDate = convertDateFormat(showDate);
                String startTime = resultSet.getString("start_time");
                String seatNames = resultSet.getString("seat_names");
                double price = resultSet.getDouble("price");

                JPanel ticketEntry = new JPanel();
                ticketEntry.setLayout(new BoxLayout(ticketEntry, BoxLayout.Y_AXIS));
                ticketEntry.setBackground(Color.decode("#ffffff"));
                Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
                Border paddingBorder = BorderFactory.createEmptyBorder(0,20,0,10);
                Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
                ticketEntry.setBorder(compoundBorder);
                JLabel ticketIdLabel = new JLabel("Ticket ID: "+ticketId);
                JLabel titleLabel = new JLabel("Movie Title: " + movieTitle);
                JLabel dateLabel = new JLabel("Show Date: " + formattedDate);
                JLabel timeLabel = new JLabel("Start Time: " + startTime);
                JLabel seatLabel = new JLabel("Seat Names: " + seatNames);
                JLabel priceLabel = new JLabel("Price: Rp." + price);

                JLabel printticketIdLabel = new JLabel(""+ticketId);
                JLabel printtitleLabel = new JLabel(movieTitle);
                JLabel printdateLabel = new JLabel(formattedDate);
                JLabel printtimeLabel = new JLabel(startTime);
                JLabel printseatLabel = new JLabel(seatNames);
                JLabel printpriceLabel = new JLabel(""+price);

                ticketEntry.add(ticketIdLabel);
                ticketEntry.add(titleLabel);
                ticketEntry.add(dateLabel);
                ticketEntry.add(timeLabel);
                ticketEntry.add(seatLabel);
                ticketEntry.add(priceLabel);
                for (Component comp : ticketEntry.getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel label = (JLabel) comp;
                        label.setAlignmentX(Component.LEFT_ALIGNMENT);
                        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    }
                }
                ticketPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                ticketEntry.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ticketEntry.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                        ticketEntry.setBackground(Color.decode("#E0E0FF"));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ticketEntry.setBorder(compoundBorder);
                        ticketEntry.setBackground(Color.decode("#ffffff"));
                    }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String[] ticketComponents = {
                                titleLabel.getText(),
                                seatLabel.getText(),
                                dateLabel.getText(),
                                timeLabel.getText(),
                                priceLabel.getText(),
                                ticketIdLabel.getText()
                        };
                        String[] ticketComponents2 ={
                                printtitleLabel.getText(),
                                printseatLabel.getText(),
                                printdateLabel.getText(),
                                printtimeLabel.getText(),
                                printpriceLabel.getText(),
                                printticketIdLabel.getText()
                        };

                        // Write the ticket details to a file
                        PrintTicket.writeTicketEntry("ticket_" + ticketId + ".txt", ticketComponents, ticketComponents2);
                    }
                });
                ticketPanel.add(ticketEntry);
            }

            if (!hasTickets) {
                JLabel noTicketsLabel = new JLabel("No tickets reserved yet.");
                noTicketsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
                noTicketsLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
                ticketPanel.add(noTicketsLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }
    }

    public static void main(String[] args) {
        Bookings bookings = new Bookings();
        bookings.setVisible(true);
    }
    public String convertDateFormat(String inputDateStr) {
        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");

            Date date = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing exception if needed
        }
        return outputDateStr;
    }
}
