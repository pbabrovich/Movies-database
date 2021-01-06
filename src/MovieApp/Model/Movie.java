package MovieApp.Model;
public class Movie {

    private int movieId;
    private String title;
    private int directorId;
    private int genreId;
    private String genre;
    private String first_name;
    private String last_name;
    private String releaseYear;
    private int rating;
    private int ratingId;
    private int isWatched;


    public Movie(int movieId, String title, String first_name, String last_name, String genre, String releaseYear, int rating, int isWatched) {
        super();
        this.movieId = movieId;
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.isWatched = isWatched;
    }
    public Movie(int movieId, String title, String first_name, String last_name, String genre, String releaseYear, int rating, int ratingId, int isWatched) {
        super();
        this.movieId = movieId;
        this.title = title;
        this.first_name = first_name;
        this.last_name = last_name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.ratingId = ratingId;
        this.isWatched = isWatched;
    }

    public Movie(int ratingId) {
        this.ratingId = ratingId;
    }

    public Movie(String title, int directorId, int genreId, String releaseYear) {
        super();
        this.title = title;
        this.directorId = directorId;
        this.genreId = genreId;
        this.releaseYear = releaseYear;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String
                .format("Movie [id=%s, Title=%s, Director id=%s, Genre id=%s, release year=%s, rating=%s]",
                        movieId, title, directorId, genreId, releaseYear, rating);
    }

    public int getIsWatched() {
        return isWatched;
    }

    public String getStatus() {
        if (isWatched == 1) {
            return "Watched";
        }
        else return "Unwatched";
    }
}
