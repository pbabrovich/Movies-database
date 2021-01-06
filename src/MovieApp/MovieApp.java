package MovieApp;

import MovieApp.GUI.MovieAppGUI;
import MovieApp.Logic.IMovieManager;
import MovieApp.Logic.MovieManager;


public class MovieApp {


    private static IMovieManager movieManager;
    private static MovieAppGUI frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
                try {
                    movieManager = new MovieManager();
                    frame = new MovieAppGUI(movieManager);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }


}
