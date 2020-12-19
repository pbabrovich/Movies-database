package MovieApp.Model;
import MovieApp.Model.Movie;
import MovieApp.MovieApp;

import java.util.List;

import javax.swing.table.AbstractTableModel;


public class MovieTableModel extends AbstractTableModel {

    public static final int OBJECT_COL = -1;
    private static final int TITLE_COL = 0;
    private static final int GENRE_COL = 1;
    private static final int DIRECTOR_COL = 2;
    private static final int RELEASE_YEAR_COL = 3;
    private static final int YOUR_RATING_COL = 4;

    private String[] columnNames = {"Title", "Genre", "Director", "Release year", "Your rating"};
    private List<Movie> movies;

    public MovieTableModel(List<Movie> theMovies) {
        movies = theMovies;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Movie tempMovie = movies.get(row);

        switch (col) {
            case TITLE_COL:
                return tempMovie.getTitle();
            case GENRE_COL:
                return tempMovie.getGenre();
            case DIRECTOR_COL:
                return tempMovie.getLast_name() + " " + tempMovie.getFirst_name();
            case RELEASE_YEAR_COL:
                return tempMovie.getReleaseYear();
            case YOUR_RATING_COL:
                return tempMovie.getRating();
            case OBJECT_COL:
                return tempMovie;
            default:
                return tempMovie.getTitle();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
