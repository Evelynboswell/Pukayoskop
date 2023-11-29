import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Jadwal {
    private static final String DB_URL = "jdbc:mariadb://qtr.h.filess.io:3305/PBOL_balloonam";
    private static final String DB_USER = "PBOL_balloonam";
    private static final String DB_PASSWORD = "d7c63c1262a8d826d469faf8f40a4ab6030fb4b3";

    public static List<String> getAvailableDays(int filmId) {
        List<String> availableDays = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT DISTINCT DATE_FORMAT(showtime_date, '%Y-%m-%d') AS day " +
                    "FROM Showtime " +
                    "WHERE film_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, filmId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String day = resultSet.getString("day");
                availableDays.add(day);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableDays;
    }

    public static List<String> getAiringTimes(int filmId, String day) {
        List<String> airingTimes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT TIME_FORMAT(start_time, '%H:%i') AS airing_time " +
                    "FROM Showtime " +
                    "WHERE film_id = ? AND DATE_FORMAT(showtime_date, '%Y-%m-%d') = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, filmId);
            preparedStatement.setString(2, day);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String airingTime = resultSet.getString("airing_time");
                airingTimes.add(airingTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airingTimes;
    }
}
