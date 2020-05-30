package io.github.arnabmaji19.libera.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianDashboardController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private Label booksCountLabel;
    @FXML
    private Label holdingsCountLabel;
    @FXML
    private Label authorsCountLabel;
    @FXML
    private Label publishersCountLabel;
    @FXML
    private Label issuesCountLabel;
    @FXML
    private Label usersCountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
