package MovieApp.Logic;

import MovieApp.DAO.MovieAppDAO;
import MovieApp.Model.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public List<Movie> getAllMovies() {
        List<Movie> movies = null;
        try {
            movies = movieAppDAO.getAllMovies();
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movie> searchMovies(String title) throws Exception {
        List<Movie> movies = new ArrayList<>();
        if (title != null && title.trim().length() > 0) {
            movies = movieAppDAO.searchMovies(title);
            return movies;
        } else
            return null;
    }

    public String addToWatched(Movie movie) throws SQLException {
        String result = null;
        if (movie.getIsWatched() == 1) {
            result = "This movie is already watched";
        } else {
            movieAppDAO.addToWatched(movie.getMovieId());
            result = "Added to watched";
        }
        return result;
    }


    @Override
    public String saveMovie(String title, String releaseYear, String director, String genre, int genreId, int directorId) throws Exception {
        Movie movie = new Movie(title, directorId, genreId, releaseYear);
        String result;
        try {
            movieAppDAO.addMovie(movie);
            result = "Movie was added";
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error while adding";
        }
        return result;
    }

    @Override
    public String rateMovie(Movie movie, int rating) throws SQLException {
        int movieId = movie.getMovieId();
        if (movie.getRatingId() == 0 && rating != 0) {
            movieAppDAO.rateMovie(movieId, rating);
            return "Rated";
        }
        if (movie.getRatingId() != 0 && rating != 0) {
            movieAppDAO.updateRating(movieId, rating);
            return "Rating updated";
        } else
            return "Error while rating";
    }

    @Override
    public List<Movie> getWatched() {
        List<Movie> movies = getAllMovies();
        List<Movie> tempMovies = new ArrayList<>();
        if (movies != null) {
            for (Movie movie : movies) {
                if (movie.getIsWatched() == 1) {
                    tempMovies.add(movie);
                }
            }
            return tempMovies;
        }
        return null;
    }

    @Override
    public String deleteMovie(Movie movie) throws SQLException {
        String result = null;
        try {
            movieAppDAO.deleteMovie(movie.getMovieId(), movie.getRatingId());
            result = "Deleted";
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error while deleting";
        }
        return result;
    }

    public static String getStatus(Movie movie) {
        if (movie.getIsWatched() == 1) {
            if (movie.getRatingId() == 0)
                return "Watched unrated";
            else
                return "Watched";
        } else return "Unwatched";
    }

    public Map<String, Integer> getSummary() {
        Map<String, Integer> summary = new TreeMap<>();
        List<Movie> movies = getAllMovies();
        int rating = 0;
        int counterWatched = 0;
        for (Movie movie : movies) {
            if (movie.getRating() != 0) {
                rating += movie.getRating();
                counterWatched++;
            }
        }
        summary.put("Average rating", rating / counterWatched);
        summary.put("Watched movies", counterWatched);
        return summary;
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
