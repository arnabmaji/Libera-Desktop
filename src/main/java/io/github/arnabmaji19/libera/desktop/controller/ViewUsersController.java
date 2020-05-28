package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.model.UserDetails;
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

    }
}
