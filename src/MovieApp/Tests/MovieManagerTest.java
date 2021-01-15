package MovieApp.Tests;

import MovieApp.DAO.IMovieAppDAO;
import MovieApp.Logic.IMovieManager;
import MovieApp.Logic.MovieManager;
import MovieApp.Model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieManagerTest {

    @Mock
    IMovieAppDAO movieAppDAO;

    static List<Movie> movies;
    IMovieManager movieManager;

    @BeforeEach
    void setUp() throws Exception {
        movies = new ArrayList<>();
        movieAppDAO = mock(IMovieAppDAO.class);
        movieManager = new MovieManager(movieAppDAO);

    }

    @Test
    void getWatchedTest() throws Exception {
        when(movieAppDAO.getWatched()).thenReturn(movies);
        List<Movie> testMovies = movieManager.getAllMovies();

        assertTrue(testMovies.isEmpty());

        movies.add(new Movie(12, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 2, true));
        movies.add(new Movie(14, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1997", 3, true));

        for (Movie movie: testMovies) {
            assertTrue(movie.getIsWatched());
        }
    }

    @Test
    void rateMovieTest() throws Exception {
        Movie movie = new Movie(12, "TestMovie", "TestName",
                        "TestLastName", "TestGenre", "1999", 2, true );
        int expected = 5;
        movieManager.rateMovie(movie, 5);
        assertEquals(movie.getRating(), expected);
    }

    @Test
    void failRateMovieTest() throws SQLException {
        Movie movie = new Movie(12, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 2, true );
        String result = movieManager.rateMovie(movie, 0);
        String expected = "Error while rating";
        assertEquals(result, expected);
    }

    @Test
    void watchedTest() {
        Movie movie1 = new Movie(12, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 2, 2, true );
        String expected = "Watched";
        assertEquals(MovieManager.getStatus(movie1),expected );
    }

    @Test
    void unwatchedTest() {
        Movie movie1 = new Movie(10, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 2, 2, false );
        String expected = "Unwatched";
        assertEquals(MovieManager.getStatus(movie1),expected );
    }

    @Test
    void watchedUnratedTest() {
        Movie movie1 = new Movie(2, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 0, 0, true );
        String expected = "Watched unrated";
        assertEquals(MovieManager.getStatus(movie1),expected );
    }

    @Test
    void alreadyInWatchLaterTest() throws Exception {

        when(movieAppDAO.checkWatchLater(anyInt())).thenReturn(true);
        Movie movie = new Movie(2, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 0, 0, true );
        String expected = "Movie is already in watch later list";
        assertEquals(movieManager.addToWatchLater(movie), expected);
    }

    @Test
    void failAddToWatchLaterTest() throws Exception {

        when(movieAppDAO.checkWatchLater(anyInt())).thenReturn(false);
        Movie movie = new Movie(2, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 0, 0, true );
        String expected = "Movie is watched";
        assertEquals(expected, movieManager.addToWatchLater(movie));
    }

    @Test
    void AddToWatchLaterTest() throws Exception {

        when(movieAppDAO.checkWatchLater(anyInt())).thenReturn(false);
        Movie movie = new Movie(2, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 0, 0, false );
        String expected = "Added to watch later list";
        assertEquals(expected, movieManager.addToWatchLater(movie));
    }

    @Test
    void searchMovieTest() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie expected = new Movie(12, "TestMovie", "TestName",
                "TestLastName", "TestGenre", "1999", 2, true);
        Movie expected2 = new Movie(14, "TestMovie2", "TestName",
                "TestLastName", "TestGenre", "1999", 2, false);
        movies.add(expected);
        movies.add(expected2);

        when(movieAppDAO.searchMovies(anyString())).thenReturn(movies);

        List<Movie> tempMovies = movieManager.searchMovies("Title");
        assertEquals(expected, tempMovies.get(0));
        assertEquals(expected2, tempMovies.get(1));
    }


    @Test
    void failSearchMovieTest() throws Exception {
        when(movieAppDAO.searchMovies(anyString())).thenReturn(null);
        List<Movie> tempMovies = movieManager.searchMovies("Title");
        assertNull(tempMovies);
    }



}