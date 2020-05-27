package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.LibrarianRequest;
import io.github.arnabmaji19.libera.desktop.model.LibrarianDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewLibrariansController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private TableView<LibrarianDetails> librariansTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadingImageView.setVisible(true);  // show the loading animation

        // Fetch all librarians
        LibrarianRequest
                .getInstance()
                .get()
                .thenAcceptAsync(list -> Platform.runLater(() -> {
                    librariansTableView.setItems(FXCollections.observableArrayList(list));
                    loadingImageView.setVisible(false);  // hide the loading animation
                }));
    }
}
