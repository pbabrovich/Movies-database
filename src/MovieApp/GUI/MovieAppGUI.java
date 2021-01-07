package MovieApp.GUI;

import MovieApp.Logic.IMovieManager;
import MovieApp.Logic.MovieManager;
import MovieApp.Model.Movie;
import MovieApp.Model.MovieTableModel;
import MovieApp.MovieApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MovieAppGUI extends JFrame {
    private final JTextField titleTextField;
    private final JTable table;

    IMovieManager movieManager;

    public MovieAppGUI(IMovieManager movieManager) {
        this.movieManager = movieManager;
        setTitle("Movie App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(900, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Enter title or genre: ");
        lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(lblNewLabel);

        titleTextField = new JTextField();
        panel.add(titleTextField);
        titleTextField.setColumns(10);

        JButton searchButton = new JButton("Search title or genre");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                searchMovie();
            }
        });
        panel.add(searchButton);

        table = new JTable();
        contentPane.add(new JScrollPane(table));


        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.SOUTH);

        JButton addNewMovie = new JButton("Add movie");
        JButton deleteNewMovie = new JButton("Delete movie");
        JButton watchedMovies = new JButton("Watched movies");
        JButton rate = new JButton("Rate selected");
        JButton addToWatched = new JButton("Add to watched");
        JButton summary = new JButton("Summary");
        JButton addToWatchLater = new JButton("Add to watch later list");
        JButton showToWatchList = new JButton("Show list of movies to watch");

        addNewMovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addNewMovie();
            }
        });

        showToWatchList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showToWatchList();
            }
        });

        addToWatchLater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWatchLater();
            }
        });

        deleteNewMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMovie();
            }
        });


        watchedMovies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getWatched();
            }
        });

        rate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rateMovie();
            }
        });

        addToWatched.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWatched();
            }
        });

        summary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summary();
            }
        });
        panel_1.add(addNewMovie);
        panel_1.add(deleteNewMovie);
        panel_1.add(watchedMovies);
        panel_1.add(rate);
        panel_1.add(addToWatched);
        panel.add(summary);
        panel_1.add(addToWatchLater);
        panel.add(showToWatchList);
        listAll();
    }

    public void addNewMovie() {
        NewMovieDialog addDialog = null;
        try {
            addDialog = new NewMovieDialog(MovieAppGUI.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addDialog.setVisible(true);
    }

    public void deleteMovie() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
                return;
            }

            int callDialog = JOptionPane.showConfirmDialog(MovieAppGUI.this,
                    "Delete this movie?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (callDialog != JOptionPane.YES_OPTION) {
                return;
            }

            Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);

            movieManager.deleteMovie(tempMovie);

            listAll();

            JOptionPane.showMessageDialog(MovieAppGUI.this, "Deleted", "Movie deleted", JOptionPane.INFORMATION_MESSAGE);


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(MovieAppGUI.this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void listAll() {
        try {
            List<Movie> list = movieManager.getAllMovies();
            MovieTableModel tableModel = new MovieTableModel(list);
            table.setModel(tableModel);
        } catch (Exception e) {

        }
    }

    public void getWatched() {
        List<Movie> movies = null;
        try {
            movies = movieManager.getWatched();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MovieTableModel tableModel = new MovieTableModel(movies);
        table.setModel(tableModel);
    }

    public void rateMovie() {
        try {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
                return;
            }
            Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);

            if (tempMovie.getRatingId() != 0) {
                int callDialog = JOptionPane.showConfirmDialog(MovieAppGUI.this,
                        "Already rated. Add new rating to this movie?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (callDialog == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            RateDialog rate = new RateDialog(MovieAppGUI.this, tempMovie);
            rate.setVisible(true);
            listAll();


        } catch (Exception exc) {
            JOptionPane.showMessageDialog(MovieAppGUI.this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void searchMovie() {
        MovieTableModel tableModel = null;
        try {
            String title = titleTextField.getText();
            List<Movie> movies = movieManager.searchMovies(title);
            if (movies != null) {
                tableModel = new MovieTableModel(movieManager.searchMovies(title));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (tableModel != null) {
            table.setModel(tableModel);
        } else {
            JOptionPane.showMessageDialog(this, "Provide title");
            listAll();
        }
    }

    public void addToWatched() {
        int rateConfirmed = 0;

        try {

            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
                return;
            }
            Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);

            int callDialog = JOptionPane.showConfirmDialog(MovieAppGUI.this,
                    "Do you want to rate the movie?", "Rate movie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (callDialog == JOptionPane.NO_OPTION) {
                movieManager.addToWatched(tempMovie, rateConfirmed);
                listAll();
                return;
            }
            rateConfirmed = 1;
            rateMovie();
            movieManager.addToWatched(tempMovie, rateConfirmed);
            listAll();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(MovieAppGUI.this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void summary() {
        int averageRating = movieManager.getSummary().get("Average rating");
        int moviesWatched = movieManager.getSummary().get("Watched movies");
        JOptionPane.showMessageDialog(this, "Average rating : " + averageRating + "\n" +
                "Watched movies : " + moviesWatched);
    }

    public void addToWatchLater() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(MovieAppGUI.this, "You must select a movie");
            return;
        }
        Movie tempMovie = (Movie) table.getValueAt(row, MovieTableModel.OBJECT_COL);
        String message = movieManager.addToWatchLater(tempMovie);
        JOptionPane.showMessageDialog(this, message);
    }

    public void showToWatchList() {
        try {
            List<Movie> list = movieManager.getWatchLaterList();
            MovieTableModel tableModel = new MovieTableModel(list);
            table.setModel(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No movies to show");
        }
    }


}
