package MovieApp.Logic;

import MovieApp.Model.Movie;
import MovieApp.MovieApp;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IMovieManager {

    public List<Movie> getAllMovies() throws Exception;

    public List<Movie> searchMovies(String title) throws Exception;

    public String saveMovie(String title, String releaseYear, String director, String genre, int genreId, int directorId) throws Exception;

    public String rateMovie(Movie movie, int rating) throws SQLException;

    public List<Movie> getWatched() throws Exception;

    public String deleteMovie(Movie movie) throws SQLException;

    public int getGenreId(String genre) throws Exception;

    public int getDirectorId(String genre) throws Exception;

    public String addToWatched(Movie tempMovie, boolean rateConfirmed) throws SQLException;

    public Map<String, Integer> getSummary();

    public String addToWatchLater(Movie movie);

    public List<Movie> getWatchLaterList();


}
