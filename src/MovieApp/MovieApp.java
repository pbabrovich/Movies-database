package MovieApp;

import MovieApp.DAO.MovieAppDAO;
import MovieApp.Logic.MovieManager;

import java.awt.EventQueue;


public class MovieApp {


    private static MovieAppDAO movieAppDAO;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
                try {
                    MovieManager movieManager = new MovieManager();
                    MovieAppGUI frame = new MovieAppGUI(movieManager);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }


}
