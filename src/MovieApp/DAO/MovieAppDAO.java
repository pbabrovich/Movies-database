package MovieApp.DAO;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import MovieApp.Model.Movie;

public class MovieAppDAO implements IMovieAppDAO {

    private Connection myConn;
    private static MovieAppDAO instance;


    public MovieAppDAO() throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("properties"));

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String dburl = props.getProperty("dburl");

        //Locale.setDefault(Locale.ENGLISH);
        // connect to database
        Class.forName("com.mysql.cj.jdbc.Driver");
        myConn = DriverManager.getConnection(dburl, user, password);

        System.out.println("DB connection successful to: " + dburl);
    }

    public static MovieAppDAO getInstance() throws Exception {
        if (instance == null) {
            instance = new MovieAppDAO();
            System.out.println("Created");
        }
        return instance;
    }

    public Connection getMyConn() {
        return myConn;
    }

    public List<Movie> getAllMovies() throws Exception {
        List<Movie> list = new ArrayList<>();

        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT a.id, a.title, b.first_name, b.last_name, c.name, a.release_year, a.rating_id, a.watched " +
                    "FROM MOVIES a INNER JOIN directors b ON a.director_id = b.id " +
                    "INNER JOIN genres c ON a.genre_id = c.id " +
                    "INNER JOIN genres d ON d.id = a.genre_id");

            while (myRs.next()) {
                Movie tempMovie = convertRowToMovie(myRs);
                list.add(tempMovie);
            }
            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    public boolean addMovie(Movie theMovie) throws Exception {
        PreparedStatement myStmt = null;

        try {
            // prepare statement
            myStmt = myConn.prepareStatement("INSERT INTO MOVIES"
                    + " (TITLE, RELEASE_YEAR, DIRECTOR_ID, GENRE_ID)"
                    + " values (?, ?, ?, ?)");

            // set params
            myStmt.setString(1, theMovie.getTitle());
            myStmt.setString(2, theMovie.getReleaseYear());
            myStmt.setInt(3, theMovie.getDirectorId());
            myStmt.setInt(4, theMovie.getGenreId());

            // execute SQL
            myStmt.executeUpdate();
            return true;

        } finally {
            close(myStmt);

        }
    }

    public boolean addToWatchLater(int id) throws Exception {
        PreparedStatement myStmt = null;

        try {
            myStmt = myConn.prepareStatement("INSERT INTO WATCH_LATER"
                    + " (MOVIE_ID)"
                    + " values (?)");

            // set params
            myStmt.setString(1, Integer.toString(id));
            // execute SQL
            myStmt.executeUpdate();
            return true;

        } finally {
            close(myStmt);
        }


    }

    public List<Movie> getToWatchList() throws SQLException {
        List<Movie> list = new ArrayList<>();

        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT a.id, a.title, b.first_name, b.last_name, c.name, a.release_year, a.rating_id, a.watched " +
                    "FROM MOVIES a INNER JOIN directors b ON a.director_id = b.id " +
                    "INNER JOIN genres c ON a.genre_id = c.id " +
                    "INNER JOIN watch_later d ON a.id = d.movie_id " +
                    "INNER JOIN genres e ON e.id = a.genre_id");

            while (myRs.next()) {
                Movie tempMovie = convertRowToMovie(myRs);
                list.add(tempMovie);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myStmt, myRs);
        }
        return list;
    }

    public boolean checkWatchLater(int id) throws Exception {
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myStmt = myConn.prepareStatement("SELECT ID FROM WATCH_LATER WHERE MOVIE_ID LIKE ?");

            // set params
            myStmt.setString(1, Integer.toString(id));
            // execute SQL
            myRs = myStmt.executeQuery();
            return myRs.next();

        } finally {
            close(myStmt);
        }

    }

    public List<Movie> searchMovies(String title) throws Exception {
        List<Movie> list = new ArrayList<>();

        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            title += "%";
            myStmt = myConn.prepareStatement("SELECT a.id, a.title, b.first_name, b.last_name, c.name, a.release_year, a.rating_id, a.watched " +
                    "FROM MOVIES a INNER JOIN directors b ON a.director_id = b.id " +
                    "INNER JOIN genres c ON a.genre_id = c.id " +
                    "INNER JOIN genres d ON d.id = a.genre_id " +
                    "WHERE a.TITLE LIKE ?");

            myStmt.setString(1, title);

            myRs = myStmt.executeQuery();

            while (myRs.next()) {
                Movie tempMovie = convertRowToMovie(myRs);
                list.add(tempMovie);
            }
            myStmt = myConn.prepareStatement("SELECT a.id, a.title, b.first_name, b.last_name, c.name, a.release_year, a.rating_id, a.watched " +
                    "FROM MOVIES a " +
                    "INNER JOIN directors b ON a.director_id = b.id " +
                    "INNER JOIN genres c ON a.genre_id = c.id " +
                    "INNER JOIN genres d ON d.id = a.genre_id " +
                    "WHERE c.NAME LIKE ?");

            myStmt.setString(1, title);

            myRs = myStmt.executeQuery();
            while (myRs.next()) {
                Movie tempMovie = convertRowToMovie(myRs);
                list.add(tempMovie);
            }

            return list;
        } finally {
            close(myStmt, myRs);
        }
    }

    public void rateMovie(int movieId, int rating) throws SQLException {
        PreparedStatement myStmt = null;

        try {
            // prepare statement

            myStmt = myConn.prepareStatement("INSERT INTO MOVIE_RATINGS"
                    + " (RATING, MOVIE_ID)"
                    + " values (?, ?)");

            // set params
            myStmt.setInt(1, rating);
            myStmt.setInt(2, movieId);
            // execute SQL
            myStmt.executeUpdate();

            ResultSet rst = null;
            myStmt = myConn.prepareStatement("SELECT ID FROM MOVIE_RATINGS WHERE MOVIE_ID LIKE ?");
            myStmt.setInt(1, movieId);
            rst = myStmt.executeQuery();
            rst.next();
            int ratingId = rst.getInt("ID");
            myStmt = myConn.prepareStatement("UPDATE MOVIES SET RATING_ID = ? WHERE ID LIKE ?");
            myStmt.setInt(1, ratingId);
            myStmt.setInt(2, movieId);
            myStmt.executeUpdate();
        } finally {
            close(myStmt);
        }
    }

    public Movie getMovie(int id) throws SQLException {
        PreparedStatement myStmt = null;
        ResultSet rst = null;
        myStmt = myConn.prepareStatement("SELECT a.ID, a.TITLE, a.DIRECTOR_ID, a.GENRE_ID, a.RELEASE_YEAR, b.RATING FROM MOVIES a " +
                "INNER JOIN MOVIE_RATINGS b ON a.RATING_ID = b.ID WHERE a.ID LIKE ?");
        myStmt.setInt(1, id);
        rst = myStmt.executeQuery();
        rst.next();
        return new Movie(rst.getInt("ID"), rst.getString("TITLE"), rst.getInt("DIRECTOR_ID"), rst.getInt("GENRE_ID"), rst.getString("RELEASE_YEAR"), rst.getInt("RATING"));
    }

    public void updateRating(int movieId, int rating) throws SQLException {
        PreparedStatement myStmt = null;

        try {
            // prepare statement

            myStmt = myConn.prepareStatement("UPDATE MOVIE_RATINGS SET RATING = ? WHERE MOVIE_ID LIKE ?");

            // set params
            myStmt.setInt(1, rating);
            myStmt.setInt(2, movieId);
            // execute SQL
            myStmt.executeUpdate();

        } finally {
            close(myStmt);
        }
    }

    public int getRating(int ratingId) throws Exception {
        PreparedStatement prepState = null;
        ResultSet myRs = null;

        try {

            prepState = myConn.prepareStatement("SELECT DISTINCT RATING FROM MOVIE_RATINGS WHERE ID LIKE ?");

            prepState.setInt(1, ratingId);

            myRs = prepState.executeQuery();

            myRs.next();
            int id = myRs.getInt("RATING");

            return id;
        } finally {
            close(prepState, myRs);
        }
    }


    public int getGenreId(String genre) throws Exception {
        PreparedStatement prepState = null;
        ResultSet myRs = null;

        try {
            genre += "%";
            prepState = myConn.prepareStatement("SELECT DISTINCT ID FROM GENRES WHERE NAME LIKE ?");

            prepState.setString(1, genre);

            myRs = prepState.executeQuery();

            myRs.next();
            int id = myRs.getInt("ID");

            return id;
        } finally {
            close(prepState, myRs);
        }
    }

    public List<Movie> getWatched() throws Exception {
        PreparedStatement prepState = null;
        ResultSet myRs = null;
        List<Movie> movies = new ArrayList<>();


        try {

            prepState = myConn.prepareStatement("SELECT a.id, a.title, b.first_name, b.last_name, c.name, a.release_year, a.rating_id, a.watched " +
                    "FROM MOVIES a " +
                    "INNER JOIN directors b ON a.director_id = b.id " +
                    "INNER JOIN genres c ON a.genre_id = c.id " +
                    "INNER JOIN genres d ON d.id = a.genre_id " +
                    "WHERE a.RATING_ID IS NOT NULL");
            myRs = prepState.executeQuery();

            while (myRs.next()) {
                Movie tempMovie = convertRowToMovie(myRs);

                movies.add(tempMovie);
            }

            return movies;
        } finally {
            close(prepState, myRs);
        }
    }

    public int getDirectorId(String name) throws Exception {
        PreparedStatement prepState = null;
        ResultSet myRs = null;
        String[] fullName = name.split(" ");

        try {
            prepState = myConn.prepareStatement("SELECT DISTINCT ID FROM DIRECTORS WHERE FIRST_NAME LIKE ? AND LAST_NAME LIKE ?");

            prepState.setString(1, fullName[0] + "%");
            prepState.setString(2, fullName[1] + "%");


            myRs = prepState.executeQuery();

            myRs.next();
            int id = myRs.getInt("ID");

            return id;
        } finally {
            close(prepState, myRs);
        }
    }

    private Movie convertRowToMovie(ResultSet myRs) throws Exception {

        int id = myRs.getInt("ID");
        String title = myRs.getString("TITLE");
        String first_name = myRs.getString("FIRST_NAME");
        String last_name = myRs.getString("LAST_NAME");
        String genre = myRs.getString("NAME");
        String release_year = myRs.getString("RELEASE_YEAR");
        boolean isWatched = myRs.getBoolean("WATCHED");

        int ratingId = myRs.getInt("RATING_ID");
        Movie tempMovie;
        if (ratingId != 0) {
            int rating = getRating(ratingId);
            tempMovie = new Movie(id, title, first_name, last_name, genre, release_year, rating, ratingId, isWatched);
        } else {
            tempMovie = new Movie(id, title, first_name, last_name, genre, release_year, ratingId, isWatched);

        }
        return tempMovie;
    }

    public void deleteMovie(int movieId, int ratingId) throws SQLException {
        PreparedStatement myPst = null;

        try {
            if (ratingId != 0) {
                myPst = myConn.prepareStatement("DELETE FROM MOVIE_RATINGS WHERE ID LIKE ?");
                myPst.setInt(1, ratingId);
                myPst.executeUpdate();
            }
            myPst = myConn.prepareStatement("DELETE FROM MOVIES WHERE ID LIKE ?");

            myPst.setInt(1, movieId);
            myPst.executeUpdate();

        } finally {
            close(myPst);
        }
    }

    @Override
    public void addToWatched(int movieId) throws SQLException {
        PreparedStatement myPst = null;

        try {
            myPst = myConn.prepareStatement("UPDATE MOVIES SET WATCHED = ? WHERE ID LIKE ?");
            myPst.setInt(1, 1);
            myPst.setInt(2, movieId);
            myPst.executeUpdate();
            System.out.println("DONE");
        } finally {
            close(myPst);
        }
    }


    private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
            throws SQLException {

        if (myRs != null) {
            myRs.close();
        }

        if (myStmt != null) {

        }

        if (myConn != null) {
            myConn.close();
        }
    }

    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
        close(null, myStmt, myRs);
    }


    private void close(Statement myStmt) throws SQLException {
        close(null, myStmt, null);
    }

}

