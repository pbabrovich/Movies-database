package MovieApp.Logic;

import MovieApp.DAO.MovieAppDAO;
import MovieApp.Model.Movie;
import java.sql.SQLException;
import java.util.List;

public class MovieManager implements IMovieManager {

    private MovieAppDAO movieAppDAO;


    public MovieManager() throws Exception {

        try {
            movieAppDAO = MovieAppDAO.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public List<Movie> getAllMovies() throws Exception {
        return movieAppDAO.getAllMovies();
    }

    @Override
    public List<Movie> searchMovies(String title) throws Exception {
        return movieAppDAO.searchMovies(title);
    }

    @Override
    public void saveMovie(Movie movie) throws Exception {
        movieAppDAO.addMovie(movie);
    }

    @Override
    public void rateMovie(int movieId, int rating) throws SQLException {
        movieAppDAO.rateMovie(movieId, rating);
    }

    @Override
    public List<Movie> getWatched() throws Exception {
    return movieAppDAO.getWatched();
    }

    @Override
    public void deleteMovie(Movie movie) throws SQLException {
        movieAppDAO.deleteMovie(movie.getMovieId(), movie.getRatingId());
    }

    @Override
    public int getGenreId(String genre) throws Exception {
        return movieAppDAO.getGenreId(genre);
    }

    @Override
    public int getDirectorId(String name) throws Exception {
        return movieAppDAO.getDirectorId(name);
    }
}
