package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.IssueRequest;
import io.github.arnabmaji19.libera.desktop.model.IssuedBook;
import io.github.arnabmaji19.libera.desktop.model.UserDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewIssuesController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TableView<IssuedBook> issuedBooksTableView;

    private UserDetails user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // update labels
        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
        emailLabel.setText(user.getEmail());

        loadingImageView.setVisible(true);

        // make an http request to populate table data
        IssueRequest
                .getInstance()
                .getIssuesForUser(user.getId())
                .thenAcceptAsync(issuedBooks -> Platform.runLater(() -> {
                    issuedBooksTableView.setItems(FXCollections.observableArrayList(issuedBooks));
                    loadingImageView.setVisible(false);
                }));
    }

    public void init(UserDetails userDetails) {
        this.user = userDetails;
    }
}
