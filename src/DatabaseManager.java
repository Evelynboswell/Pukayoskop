import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private static List<String> selectedSeats = new ArrayList<>();
    private static final double SERVICE_FEE = 3000.0;
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
    public static void addSelectedSeat(String seatName) {
        selectedSeats.add(seatName);
    }
    public static double getServiceFee() {
        return SERVICE_FEE;
    }
    public static void clearSelectedSeats() {
        selectedSeats.clear();
    }

    // Kelas ini mengambil informasi terkait film dari database
    public static class MovieDetails {
        private String title;
        private String genre;
        private String duration;
        private String rating;
        private String description;
        private String imageUrl;
        // Setter dan Getter 
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

    // Kelas ini mengambil jadwal penayangan film dari database 
    public static class MovieShowtime {
        private String[] showtimeDates;
        private String[] startTimes;
        private int currentIndex;
        // Constructor
        public MovieShowtime(int size) {
            this.showtimeDates = new String[size];
            this.startTimes = new String[size];
            this.currentIndex = 0;
        }
        // Setter dan Getter
        public String[] getShowtimeDates() {
            return showtimeDates;
        }
        public String[] getStartTimes() {
            return startTimes;
        }
        // Method untuk menambahkan hari dan jam ke array
        public void addShowtime(String showtimeDate, String startTime) {
            // Cek apakah jam tayang sudah ada di dalam array
            for (int i = 0; i < currentIndex; i++) {
                if (startTimes[i] != null && startTimes[i].equals(startTime)) {
                    // jika jam tayang sudah ada maka tidak dimasukkan lagi
                    return;
                }
                if(showtimeDates[i] != null && showtimeDates[i].equals(showtimeDate)){
                    return;
                }
            }

            // menambahkan data jika nilainya unik/berbeda dari yg lain
            showtimeDates[currentIndex] = showtimeDate;
            startTimes[currentIndex] = startTime;
            currentIndex++;
        }
    }
    // Method untuk mengotentifikasi user
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
                return true; // Valid 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Menutup ResultSet, PreparedStatement, dan Connection dalam urutan terbalik
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
        return false; // Validasi gagal
    }
    // Method untuk memasukkan data user ke database
    public static boolean insertUser(String fullname, String username, String password, String userphone) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String checkQuery = "SELECT username FROM Users WHERE username = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Username sudah ada di database, kembalikan nilai false
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
    // Method untuk mengambil film dari database
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
    // Method untuk mengambil informasi dari film yang dipilih
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
        }
        return movieDetails;
    }
    
    // Kelas ini mengambil jadwal film yang telah dipilih user dari kelas JadwalFilm
    public static class SelectedMovieDetails {
        private String selectedDay;
        private String selectedTime;
        private int showtimeId;
        // Constructor
        public SelectedMovieDetails(String selectedDay, String selectedTime, int showtimeId) {
            this.selectedDay = selectedDay;
            this.selectedTime = selectedTime;
            this.showtimeId = showtimeId;
        }
        // Method untuk menampilkan jadwal film yang dipilih
        public void displaySelectedDetails() {
            System.out.println("Selected Day: " + selectedDay);
            System.out.println("Selected Time: " + selectedTime);
            System.out.println("Showtime ID: " + showtimeId);
        }
        // Setter dan Getter
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

    // Method untuk mengambil showtime_id dari sebuah film dari database
    public static int getShowtimeId(int filmId, String selectedDay, String selectedTime) {
        int showtimeId = -1; // Nilai default jika id tidak ditemukan
        String query = "SELECT showtime_id, DAYNAME(showtime_date) AS day_name, TIME(start_time) AS start_time FROM Showtime WHERE film_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, filmId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String dayName = resultSet.getString("day_name");
                    String startTime = resultSet.getString("start_time");

                    // Konversi format hari dan jam 
                    int formattedDay = convertDay(dayName);
                    String formattedTime = convertTime(startTime);

                    // Cek apakah hari dan jam yang diambil cocok dengan yg dipilih
                    if (formattedDay == convertDay(selectedDay) && formattedTime.equals(selectedTime)) {
                        showtimeId = resultSet.getInt("showtime_id");
                        break; // Exit the loop if found
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
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
                return -1;
        }
    }

    private static String convertTime(String time) {
        return time.substring(0, 5); // Mengembalikan dalam bentuk HH:MM
    }
    // Method untuk mengambil jam tayang film 
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

                    // Mengubah format jam menjadi HH:MM
                    String trimmedTime = startTime.substring(0, 5);

                    LocalDate date = LocalDate.parse(showtimeDate);
                    String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                    movieShowtime.addShowtime(dayName, trimmedTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieShowtime;
    }
    // Method untuk menambahkan kursi yang dipilih ke dalam tabel Seats
    public static void insertSeat(int filmId, int showtimeId, String seatName) {
        double seatPrice = 30000.0;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO Seats (film_id, showtime_id, seat_name, seat_price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, filmId);
                statement.setInt(2, showtimeId);
                statement.setString(3, seatName);
                statement.setDouble(4, seatPrice);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Seat inserted successfully: " + seatName);
                } else {
                    System.out.println("Failed to insert seat: " + seatName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    // Method untuk mengambil kursi yang telah dibooking
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
        }
        return bookedSeats;
    }
    // Method untuk mengambil kursi yang dipilih user
    public static List<String> getSelectedSeats(int filmId, int showtimeId, int selectedSeatsCounter) {
        List<String> selectedSeats = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT seat_name FROM Seats WHERE film_id = ? AND showtime_id = ? ORDER BY seat_id DESC LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, filmId);
            statement.setInt(2, showtimeId);
            statement.setInt(3, selectedSeatsCounter);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String seatName = resultSet.getString("seat_name");
                selectedSeats.add(seatName);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selectedSeats;
    }
    // Menghitung harga total kursi
    public static double getTotalSeatPrice(int filmId, int showtimeId, List<String> selectedSeats) {
        double totalSeatPrice = 0.0;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            for (String seatName : selectedSeats) {
                String query = "SELECT seat_price FROM Seats WHERE film_id = ? AND showtime_id = ? AND seat_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, filmId);
                statement.setInt(2, showtimeId);
                statement.setString(3, seatName);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    double seatPrice = resultSet.getDouble("seat_price");
                    totalSeatPrice += seatPrice;
                }

                resultSet.close();
                statement.close();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSeatPrice;
    }
    public static double calculateTotalPrice(double totalSeatPrice) {
        double serviceFee = getServiceFee();
        return totalSeatPrice + serviceFee;
    }
    public static String getShowtimeDate(int showtimeId) {
        String showtimeDateFormatted = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT showtime_date FROM Showtime WHERE showtime_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, showtimeId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDate showtimeDate = resultSet.getDate("showtime_date").toLocalDate();
                // Format tanggal dibuat sedemikian rupa "Hari, tanggal bulan tahun"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
                showtimeDateFormatted = showtimeDate.format(formatter);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimeDateFormatted;
    }
    public static void insertTicket(int userId, int filmId, int showtimeId, List<String> selectedSeats, double totalPrice) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            for (String seatName : selectedSeats) {
                int seatId = getSeatId(filmId, showtimeId, seatName); // Mengambil seat_id dari tabel Seats dari database
                String query = "INSERT INTO Tickets (user_id, film_id, showtime_id, seat_id, price) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, filmId);
                statement.setInt(3, showtimeId);
                statement.setInt(4, seatId);
                statement.setDouble(5, totalPrice);

                statement.executeUpdate();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static int getSeatId(int filmId, int showtimeId, String seatName) {
        int seatId = -1;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT seat_id FROM Seats WHERE film_id = ? AND showtime_id = ? AND seat_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, filmId);
            statement.setInt(2, showtimeId);
            statement.setString(3, seatName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                seatId = resultSet.getInt("seat_id");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing SQL query: " + e.getMessage());
        }

        return seatId;
    }
    public static void deleteSeats(int filmId, int showtimeId, int selectedSeatsCounter) {
        if (filmId <= 0 || showtimeId <= 0 || selectedSeatsCounter <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM Seats WHERE film_id = ? AND showtime_id = ? ORDER BY seat_id DESC LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, filmId);
            statement.setInt(2, showtimeId);
            statement.setInt(3, selectedSeatsCounter);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                System.out.println("No seats were deleted.");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to delete seats. Please try again later.");
        }
    }

    public static int getUserId(String username) {
        int userId = -1; // Nilai default jika userId tidak ditemukan

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT user_id FROM Users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving user ID from the database.");
        }

        return userId;
    }
    public static ResultSet getTicketsDetails(int userId) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT t.ticket_id, t.user_id, u.username, f.title, s.showtime_date, s.start_time, " +
                    "GROUP_CONCAT(st.seat_name SEPARATOR ', ') as seat_names, t.price " +
                    "FROM Tickets t " +
                    "INNER JOIN Users u ON t.user_id = u.user_id " +
                    "INNER JOIN Film f ON t.film_id = f.film_id " +
                    "INNER JOIN Seats st ON t.seat_id = st.seat_id " +
                    "INNER JOIN Showtime s ON st.showtime_id = s.showtime_id " +
                    "WHERE t.user_id = ? " +
                    "GROUP BY t.film_id, t.showtime_id";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
