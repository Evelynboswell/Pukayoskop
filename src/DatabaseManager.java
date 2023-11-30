import java.sql.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mariadb://qtr.h.filess.io:3305/PBOL_balloonam";
    private static final String DB_USER = "PBOL_balloonam";
    private static final String DB_PASSWORD = "d7c63c1262a8d826d469faf8f40a4ab6030fb4b3";
    private static String username;
    private static SelectedMovieDetails selectedMovieDetails;
    private static int selectedFilmId = -1;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String user) {
        username = user;
    }
    public static int getSelectedFilmId() {
        return selectedFilmId;
    }

    public static void setSelectedFilmId(int filmId) {
        selectedFilmId = filmId;
    }
    public static class MovieDetails {
        private String title;
        private String genre;
        private String duration;
        private String rating;
        private String description;
        private String imageUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        // Constructor
        public MovieDetails(String title, String genre, String duration, String rating, String description, String imageUrl) {
            this.title = title;
            this.genre = genre;
            this.duration = duration;
            this.rating = rating;
            this.description = description;
            this.imageUrl = imageUrl;
        }
    }
    public static class MovieShowtime {
        private String[] showtimeDates;
        private String[] startTimes;
        private int currentIndex;

        public MovieShowtime(int size) {
            this.showtimeDates = new String[size];
            this.startTimes = new String[size];
            this.currentIndex = 0;
        }

        public String[] getShowtimeDates() {
            return showtimeDates;
        }

        public String[] getStartTimes() {
            return startTimes;
        }

        public void addShowtime(String showtimeDate, String startTime) {
            // Check if the startTime already exists in the array
            for (int i = 0; i < currentIndex; i++) {
                if (startTimes[i] != null && startTimes[i].equals(startTime)) {
                    // If the start time already exists, don't add it again
                    return;
                }
                if(showtimeDates[i] != null && showtimeDates[i].equals(showtimeDate)){
                    return;
                }
            }

            // Add the showtime if it's unique
            showtimeDates[currentIndex] = showtimeDate;
            startTimes[currentIndex] = startTime;
            currentIndex++;
        }

    }

    public static boolean validateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                setUsername(username);
                return true; // User is validated
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the ResultSet, PreparedStatement, and Connection in reverse order
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false; // Validation failed
    }

    public static boolean insertUser(String fullname, String username, String password, String userphone) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String checkQuery = "SELECT username FROM Users WHERE username = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Username already exists, return false
                return false;
            } else {
                String insertQuery = "INSERT INTO Users (fullname, username, password, user_phone) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(insertQuery);
                statement.setString(1, fullname);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, userphone);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static ResultSet getMovies() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT film_id, title FROM Film";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return resultSet;
    }
    public static MovieDetails getMovieDetails(int filmId) {
        MovieDetails movieDetails = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT title, genre, duration, rating, description, image_path FROM Film WHERE film_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, filmId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    movieDetails = new MovieDetails(
                            resultSet.getString("title"),
                            resultSet.getString("genre"),
                            resultSet.getString("duration"),
                            resultSet.getString("rating"),
                            resultSet.getString("description"),
                            resultSet.getString("image_path")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors
        }

        return movieDetails;
    }
    // This class fetches the selected day and  time from JadwalFilm class
    public static class SelectedMovieDetails {
        private String selectedDay;
        private String selectedTime;
        private int showtimeId;

        public SelectedMovieDetails(String selectedDay, String selectedTime, int showtimeId) {
            this.selectedDay = selectedDay;
            this.selectedTime = selectedTime;
            this.showtimeId = showtimeId;
        }

        public void displaySelectedDetails() {
            System.out.println("Selected Day: " + selectedDay);
            System.out.println("Selected Time: " + selectedTime);
            System.out.println("Showtime ID: " + showtimeId);
        }

        public String getSelectedDay() {
            return selectedDay;
        }

        public String getSelectedTime() {
            return selectedTime;
        }

        public int getShowtimeId() {
            return showtimeId;
        }
    }
    public static void setSelectedMovieDetails(SelectedMovieDetails movieDetails) {
        // Store the selected movie details for access by other classes
        selectedMovieDetails = movieDetails;
    }

    public static SelectedMovieDetails getSelectedMovieDetails() {
        return selectedMovieDetails;
    }
    public static int getShowtimeId(int filmId, String selectedDay, String selectedTime) {
        int showtimeId = -1; // Default value if no ID is found
        String query = "SELECT showtime_id, DAYNAME(showtime_date) AS day_name, TIME(start_time) AS start_time FROM Showtime WHERE film_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, filmId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String dayName = resultSet.getString("day_name");
                    String startTime = resultSet.getString("start_time");

                    // Convert dayName and startTime to the desired format
                    int formattedDay = convertDay(dayName);
                    String formattedTime = convertTime(startTime);

                    // Check if the retrieved day and time match the selected ones
                    if (formattedDay == convertDay(selectedDay) && formattedTime.equals(selectedTime)) {
                        showtimeId = resultSet.getInt("showtime_id");
                        break; // Exit the loop if found
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle potential exceptions
        }

        return showtimeId;
    }

    private static int convertDay(String day) {
        switch (day) {
            case "Monday":
                return 11;
            case "Tuesday":
                return 12;
            case "Wednesday":
                return 13;
            case "Thursday":
                return 14;
            case "Friday":
                return 15;
            case "Saturday":
                return 16;
            default:
                return -1; // Invalid day format
        }
    }

    private static String convertTime(String time) {
        return time.substring(0, 5); // Return only HH:MM part
    }

    public static MovieShowtime getMovieShowtime(int filmId) {
        MovieShowtime movieShowtime = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String countQuery = "SELECT COUNT(*) AS count FROM Showtime WHERE film_id = ?";
            try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
                countStatement.setInt(1, filmId);
                ResultSet countResultSet = countStatement.executeQuery();
                countResultSet.next();
                int rowCount = countResultSet.getInt("count");

                movieShowtime = new MovieShowtime(rowCount);
            }

            String query = "SELECT DISTINCT showtime_date, start_time FROM Showtime WHERE film_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, filmId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String showtimeDate = resultSet.getString("showtime_date");
                    String startTime = resultSet.getString("start_time");

                    // Trimming the time to HH:mm format
                    String trimmedTime = startTime.substring(0, 5);

                    LocalDate date = LocalDate.parse(showtimeDate);
                    String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                    movieShowtime.addShowtime(dayName, trimmedTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors
        }

        return movieShowtime;
    }
    public static void insertSeat(int filmId, int showtimeId, String seatName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Seats (film_id, showtime_id, seat_name) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, filmId);
                statement.setInt(2, showtimeId);
                statement.setString(3, seatName);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Seat inserted successfully: " + seatName);
                } else {
                    System.out.println("Failed to insert seat: " + seatName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQL errors
        }
    }
    public static List<String> getBookedSeats(int filmId, int showtimeId) {
        List<String> bookedSeats = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT seat_name FROM Seats WHERE film_id = ? AND showtime_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, filmId);
                statement.setInt(2, showtimeId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String seatName = resultSet.getString("seat_name");
                    bookedSeats.add(seatName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors
        }
        return bookedSeats;
    }
}
