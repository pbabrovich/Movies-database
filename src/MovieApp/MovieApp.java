package MovieApp;

import MovieApp.DAO.IMovieAppDAO;
import MovieApp.DAO.MovieAppDAO;
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
                    IMovieAppDAO movieAppDAO = new MovieAppDAO();
                    movieManager = new MovieManager(movieAppDAO);
                    frame = new MovieAppGUI(movieManager);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }


}
