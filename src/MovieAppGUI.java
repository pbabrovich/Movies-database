
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieAppGUI extends JFrame {

    private JPanel contentPane;
    private final JPanel panel = new JPanel();
    private JTextField titleTextField;
    private JTable table;
    private MovieApp movieApp;
    private JPanel panel_1;
    private JButton addNewMovie;
    private JButton deleteNewMovie;
    private JButton watchedMovies;
    private JButton rate;
    private JButton showActors;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MovieAppGUI frame = new MovieAppGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MovieAppGUI() {

        try {

            movieApp = new MovieApp();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, " Error: ", JOptionPane.ERROR_MESSAGE);
        }
        setTitle("Movie App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Enter title or genre: ");
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(lblNewLabel);

        titleTextField = new JTextField();
        panel.add(titleTextField);
        titleTextField.setColumns(10);

        JButton btnNewButton = new JButton("Search title or genre");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                try {
                    String title = titleTextField.getText();
                    List<Movie> movies = null;

                    if (title != null && title.trim().length() > 0) {
                        movies = movieApp.searchMovies(title);
                    } else {
                        movies = movieApp.getAllMovies();
                    }


                    MovieTableModel tableModel = new MovieTableModel(movies);
                    table.setModel(tableModel);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MovieAppGUI.this, "Error" + e, "Error ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnNewButton);

        table = new JTable();
        contentPane.add(new JScrollPane(table));


        panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.SOUTH);

        addNewMovie = new JButton("Add movie");
        deleteNewMovie = new JButton("Delete movie");
        watchedMovies = new JButton("Watched movies");
        showActors = new JButton("Show actors");
        rate = new JButton("Rate selected and add to watched");

        addNewMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                AddMovieDialog addDialog = null;
                try {
                    addDialog = new AddMovieDialog(movieApp, MovieAppGUI.this);
                    addDialog.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });


        deleteNewMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
                        return;
                    }

                    int callDialog = JOptionPane.showConfirmDialog(MovieAppGUI.this, "Delete this movie?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (callDialog != JOptionPane.YES_OPTION) {
                        return;
                    }

                    Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);

                    movieApp.deleteMovie(tempMovie.getMovieId(), tempMovie.getRatingId());

                    refreshTable();

                    JOptionPane.showMessageDialog(MovieAppGUI.this, "Deleted", "Movie deleted", JOptionPane.INFORMATION_MESSAGE);


                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(MovieAppGUI.this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
                }


            }
        });

        watchedMovies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Movie> movies = null;
                try {
                    movies = movieApp.getWatched();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                MovieTableModel tableModel = new MovieTableModel(movies);
                table.setModel(tableModel);
            }
        });

        rate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int row = table.getSelectedRow();
                    if (row < 0) {
                        JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
                        return;
                    }


                    Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);

                    if (tempMovie.getRatingId() != 0) {
                        JOptionPane.showMessageDialog(MovieAppGUI.this, "Already rated");
                    } else {
                        RateDialog rate = new RateDialog(tempMovie.getMovieId(), movieApp, MovieAppGUI.this);
                        rate.setVisible(true);
                    }



                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(MovieAppGUI.this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });


        panel_1.add(addNewMovie);
        panel_1.add(deleteNewMovie);
        panel_1.add(watchedMovies);
        panel_1.add(showActors);
        panel_1.add(rate);
    }

    public void refreshTable() {
        try {
            List<Movie> list = movieApp.getAllMovies();

            MovieTableModel tableModel = new MovieTableModel(list);
            table.setModel(tableModel);
        } catch (Exception e) {

        }
    }

}
