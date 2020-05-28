package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.UserRequest;
import io.github.arnabmaji19.libera.desktop.model.UserDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class ViewUsersController {
    @FXML
    private ImageView loadingImageView;
    @FXML
    private TableView<UserDetails> usersTableView;


    @FXML
    private void refreshTableData() {
        /*
         * Refresh table data
         */

        loadingImageView.setVisible(true);

        // make an http request to get all user details
        UserRequest
                .getInstance()
                .get()
                .thenAcceptAsync(users -> Platform.runLater(() -> {
                    usersTableView.setItems(FXCollections.observableArrayList(users));
                    loadingImageView.setVisible(false);
                }));

    }
}
