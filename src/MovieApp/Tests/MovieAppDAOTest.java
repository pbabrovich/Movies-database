package MovieApp.Tests;

import MovieApp.DAO.IMovieAppDAO;
import MovieApp.DAO.MovieAppDAO;
import MovieApp.Logic.MovieManager;
import MovieApp.Model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieAppDAOTest {

    Movie movie;
    IMovieAppDAO instanceDao;
    MovieManager movieManager;
    String expectedTitle;
    int expectedDirID;
    int expectedGenreID;
    String expectedReleaseYear;


    @BeforeEach
    void setUp() throws Exception {
        instanceDao = MovieAppDAO.getInstance();
        movieManager = new MovieManager(instanceDao);
        expectedTitle = "Title to test";
        expectedDirID = 1;
        expectedReleaseYear = "1999";

    }

    @Test
    void addMovieTest() throws Exception {

        Movie movie = new Movie(expectedTitle, expectedDirID, expectedGenreID, expectedReleaseYear);
        assertTrue(instanceDao.addMovie(movie));

        Movie testMovie = instanceDao.searchMovies(expectedTitle).get(0);

        assertEquals(expectedTitle, testMovie.getTitle());
        assertEquals(expectedReleaseYear, testMovie.getReleaseYear());

    }


    @Test
    void updateRatingTest() throws SQLException {
        int expectedRating = 5;
        instanceDao.updateRating(1, expectedRating);
        Movie tempMovie = instanceDao.getMovie(1);
        assertEquals(5, tempMovie.getRating());
    }

    @Test
    void rateMovieTest() throws SQLException {
        int expectedRating = 5;
        instanceDao.rateMovie(23, expectedRating);
        Movie tempMovie = instanceDao.getMovie(23);
        assertEquals(5, tempMovie.getRating());
    }

    @Test
    void getWatchedTest() {
        List<Movie> movies = movieManager.getWatched();
        for (Movie movie: movies) {
            assertTrue(movie.getIsWatched());
        }
    }

    @Test void addToWatchLaterTest() throws Exception {
        assertTrue(instanceDao.addToWatchLater(23));



    }


}