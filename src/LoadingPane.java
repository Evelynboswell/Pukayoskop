import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingPane extends JFrame {
    private JLabel loadingLabel;
    private Timer timer;
    private int dotCount = 0;

    public LoadingPane() {
        setSize(300, 100);
        setLocationRelativeTo(null); // Memposisikan frame pada tengah layar
        setResizable(false);
        setTitle("Loading...");

        loadingLabel = new JLabel("Loading, please wait");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(loadingLabel);

        // Membuat timer 
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLoadingText();
            }
        });
        timer.start();

        // Timer berhenti ketika window ditutup
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                timer.stop();
            }
        });
    }

    private void updateLoadingText() {
        dotCount = (dotCount + 1) % 4;
        StringBuilder text = new StringBuilder("Loading, please wait");
        for (int i = 0; i < dotCount; i++) {
            text.append('.');
        }
        loadingLabel.setText(text.toString());
    }

    public void setLoadingText(String text) {
        loadingLabel.setText(text);
    }
}
