package MovieApp.Logic;

import MovieApp.Model.Movie;
import MovieApp.MovieApp;

import java.sql.SQLException;
import java.util.List;

public interface IMovieManager {

public List<Movie> getAllMovies() throws Exception;
public List<Movie> searchMovies(String title) throws Exception;
public void saveMovie(Movie movie) throws Exception;
public void rateMovie(int movieId, int rating) throws SQLException;
public List<Movie> getWatched() throws Exception;
public void deleteMovie(Movie movie) throws SQLException;
public int getGenreId(String genre) throws Exception;
public int getDirectorId(String genre) throws Exception;

}
