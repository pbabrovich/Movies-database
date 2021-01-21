package MovieApp;

import MovieApp.Logic.IMovieManager;
import MovieApp.Model.Movie;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MovieAppFacade {
    private IMovieManager movieManager;

    public MovieAppFacade(IMovieManager movieManager) {
        this.movieManager = movieManager;

    }

    public String addNewMovie(String title, String releaseYear, String director, String genre, int genreId, int directorId) {
        String result = null;
        try {
            result = movieManager.saveMovie(title, releaseYear, director, genre, genreId, directorId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String deleteMovie(Movie movie) {
        String result = null;
        try {
            result = movieManager.deleteMovie(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Movie> listAll() {
        List<Movie> movies = null;
        try {
            movies = movieManager.getAllMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void rateMovie(Movie movie, int rating) {
        try {
            movieManager.rateMovie(movie, rating);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public List<Movie> getWatched() {
        List<Movie> movies = null;
        try {
            movies = movieManager.getWatched();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return movies;
    }


    public List<Movie> searchMovie(String title) {
        List<Movie> movies = null;
        try {
            movies = movieManager.searchMovies(title);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return movies;
    }

    public void addToWatched(Movie movie, boolean rateConfirmed) {
        try {
            movieManager.addToWatched(movie, rateConfirmed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> summary() {
        Map<String, Integer> summary = null;
        try {
            summary = movieManager.getSummary();
        } catch (Exception excep) {
            excep.printStackTrace();
        }
        return summary;
    }

    public String addToWatchLater(Movie movie) {
        String result = null;
        try {
            result = movieManager.addToWatchLater(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Movie> showToWatchList() {
        List<Movie> movies = null;
        try {
            movies = movieManager.getWatchLaterList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> getWatchLater() {
        List<Movie> movies = null;
        try {
            movies = movieManager.getWatchLaterList();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return movies;
    }


}
