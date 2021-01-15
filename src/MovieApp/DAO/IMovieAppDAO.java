package MovieApp.DAO;

import MovieApp.Model.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieAppDAO {

    public List<Movie> getAllMovies() throws Exception;

    public boolean addMovie(Movie theMovie) throws Exception;

    public List<Movie> searchMovies(String title) throws Exception;

    public void rateMovie(int movieId, int rating) throws SQLException;

    public int getRating(int ratingId) throws Exception;

    public int getGenreId(String genre) throws Exception;

    public List<Movie> getWatched() throws Exception;

    public int getDirectorId(String name) throws Exception;

    public void deleteMovie(int movieId, int ratingId) throws SQLException;

    public boolean addToWatchLater(int id) throws Exception;

    public boolean checkWatchLater(int id) throws Exception;

    public List<Movie> getToWatchList() throws SQLException;

    public void updateRating(int movieId, int rating) throws SQLException;

    void addToWatched(int movieId) throws SQLException;

    public Movie getMovie(int id) throws SQLException;

}
