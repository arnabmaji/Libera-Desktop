package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.UserRequest;
import io.github.arnabmaji19.libera.desktop.model.IssuedBook;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserIssuesController implements Initializable {
    @FXML
    private ImageView loadingImageView;
    @FXML
    private TableView<IssuedBook> issuedBooksTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadingImageView.setVisible(true);

        // Make an request to get all issued book for currently logged in user
        UserRequest
                .getInstance()
                .getIssuesForUser()
                .thenAcceptAsync(issuedBooks -> Platform.runLater(() -> {
                    issuedBooksTableView.setItems(FXCollections.observableArrayList(issuedBooks));
                    loadingImageView.setVisible(false);
                }));
    }
}
