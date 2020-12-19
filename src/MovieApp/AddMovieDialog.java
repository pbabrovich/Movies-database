package MovieApp;

import MovieApp.Model.Movie;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import MovieApp.DAO.MovieAppDAO;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMovieDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField titleTextField;
    private JTextField releaseTextField;
    private JComboBox directorCombo;
    private JComboBox genresComboBox;
    private Statement myStmt = null;
    private ResultSet rst = null;
    private MovieAppGUI movieAppGUI;
    private MovieAppDAO movieAppDAO;

    /**
     * Create the dialog.
     */
    public AddMovieDialog (MovieAppGUI movieAppGUI) throws Exception {
        this();
        fillCombo();
        this.movieAppGUI = movieAppGUI;
        try {
            this.movieAppDAO = MovieAppDAO.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public AddMovieDialog() throws SQLException {

        setTitle("Add movie to base ");
        setBounds(600, 300, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),},
                new RowSpec[] {
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,}));
        {
            JLabel titleLable = new JLabel("Title: ");
            contentPanel.add(titleLable, "2, 2, right, default");
        }
        {
            titleTextField = new JTextField();
            contentPanel.add(titleTextField, "4, 2, fill, default");
            titleTextField.setColumns(10);
        }
        {
            JLabel releaseYearLabel = new JLabel("Release year: ");
            contentPanel.add(releaseYearLabel, "2, 4, right, default");
        }
        {
            releaseTextField = new JTextField();
            releaseTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                }
            });
            releaseTextField.setDocument(new LimitCharTextField(4));


            contentPanel.add(releaseTextField, "4, 4, fill, default");
            releaseTextField.setColumns(10);
        }
        {
            JLabel genreLabel = new JLabel("Director: ");
            contentPanel.add(genreLabel, "2, 6, right, default");
        }
        {
            directorCombo = new JComboBox();
            contentPanel.add(directorCombo, "4, 6, fill, default");

        }
        {
            JLabel DirectorLabel = new JLabel("Genre: ");
            contentPanel.add(DirectorLabel, "2, 8, right, default");
        }
        {
            genresComboBox = new JComboBox();
            contentPanel.add(genresComboBox, "4, 8, fill, default");
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        try {
                            saveMovie();
                            movieAppGUI.refreshTable();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                saveButton.setActionCommand("OK");
                buttonPane.add(saveButton);
                getRootPane().setDefaultButton(saveButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        movieAppGUI.refreshTable();
                    }
                });
            }
        }

    }


    private void saveMovie() throws Exception {
        String title = titleTextField.getText();
        String releaseYear = releaseTextField.getText();
        String director = directorCombo.getSelectedItem().toString();
        String genre = genresComboBox.getSelectedItem().toString();

        int genreId = movieAppGUI.movieManager.getGenreId(genre);
        int directorId = movieAppGUI.movieManager.getDirectorId(director);


        Movie tempMovie = new Movie(title, directorId, genreId, releaseYear );
        try {
            movieAppGUI.movieManager.saveMovie(tempMovie);
            JOptionPane.showMessageDialog(this,"Added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fillCombo () throws Exception {
        myStmt = MovieAppDAO.getInstance().getMyConn().createStatement();
        rst = myStmt.executeQuery("SELECT * FROM GENRES");
        while (rst.next()) {
            String result = rst.getString("NAME");
            genresComboBox.addItem(result);
        }
        rst = myStmt.executeQuery("SELECT * FROM DIRECTORS");
        while (rst.next()) {
            String result = rst.getString("FIRST_NAME") + " " + rst.getString("LAST_NAME");
            directorCombo.addItem(result);
        }
        myStmt.close();
        rst.close();
    }


}