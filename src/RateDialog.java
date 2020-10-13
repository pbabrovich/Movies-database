import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RateDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JComboBox<Integer> rateBox;
    private int movieId;
    private MovieApp movieApp;
    private MovieAppGUI movieAppGUI;

    /**
     * Create the dialog.
     */
    public RateDialog(int movieId, MovieApp movieApp, MovieAppGUI movieAppGUI) {
        this.movieId = movieId;
        this.movieApp = movieApp;
        this.movieAppGUI = movieAppGUI;
        setTitle("Rate movie");
        setBounds(800, 300, 200, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel lblNewLabel = new JLabel("Chose your rating for this movie");
        contentPanel.add(lblNewLabel);


        rateBox = new JComboBox<Integer>();
        rateBox.addItem(5);
        rateBox.addItem(4);
        rateBox.addItem(3);
        rateBox.addItem(2);
        rateBox.addItem(1);

        contentPanel.add(rateBox);


        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Save");
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    movieApp.rateMovie(movieId, Integer.parseInt(rateBox.getSelectedItem().toString()));
                    JOptionPane.showMessageDialog(RateDialog.this, "Rated");
                    movieAppGUI.refreshTable();
                    dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);


    }

}
