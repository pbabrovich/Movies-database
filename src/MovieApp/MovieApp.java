package MovieApp;

import MovieApp.DAO.MovieAppDAO;
import java.awt.EventQueue;


public class MovieApp {


    private static MovieAppDAO movieAppDAO;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
                try {
                    movieAppDAO = MovieAppDAO.getInstance();
                    MovieAppGUI frame = new MovieAppGUI(movieAppDAO);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }


}
