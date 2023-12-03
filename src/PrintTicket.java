import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PrintTicket {
    public static void writeTicketEntry(String filename, String[] components1, String[] components2) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("------------------------------------------------------___-------------------------------------\n");
            writer.write("PUKAYOSKOP PRESENTS                                    |   MOVIE TICKET                      [\n");
            writer.write("                                                       |                                      ]\n");
            writer.write(components1[0] + "\t\t\t\t   |   " + components2[0] + "\t\t\t [\n");
            writer.write("                                                       |                                      ]\n");
            writer.write("SCREEN                                                 |                                     [\n");
            writer.write("REGULER                                                |   REGULER                            ]\n");
            writer.write("_______________________________________________________|                                     [\n");
            writer.write("                                                       |                                      ]\n");
            writer.write("" + components1[1] + "\t\t\t\t\t\t\t\t\t   |   " + components2[1] + "\t\t\t\t\t\t\t [\n");
            writer.write("" + components1[2] + "\t\t\t\t   |   " + components2[2] + "\t\t  ]\n");
            writer.write("" + components1[3] + "\t\t\t\t\t\t\t\t   |   " + components2[3] + "                          [\n");
            writer.write("" + components1[4] + "\t\t\t\t\t\t\t\t\t   |   " + components2[4] + "\t\t\t\t\t\t\t  ]\n");
            writer.write("" + components1[5] + "\t\t\t\t\t\t\t\t\t\t   |   " + components2[5] + "\t\t\t\t\t\t\t\t [\n");
            writer.write("                                                       |                                      ]\n");
            writer.write("______________________________________________________---____________________________________]\n");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException if needed
        }
    }

    public static void main(String[] args) {
        String[] components1 = {
                "TITLELABEL (make the size big)",
                "REGULER (make the font big)",
                "SEATLABEL",
                "DATELABEL",
                "TIMELABEL",
                "PRICELABEL",
                "TICKETIDLABEL"
        };

        String[] components2 = {
                "TITLELABEL",
                "REGULER",
                "SEATLABEL",
                "DATELABEL",
                "TIMELABEL",
                "PRICELABEL",
                "TICKETIDLABEL"
        };

        writeTicketEntry("ticket.txt", components1, components2);
    }
}
