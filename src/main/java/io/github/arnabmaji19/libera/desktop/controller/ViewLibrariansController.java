package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.LibrarianRequest;
import io.github.arnabmaji19.libera.desktop.model.LibrarianDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewLibrariansController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private TableView<LibrarianDetails> librariansTableView;

    private Stage addLibrarianStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void refreshTableData() {
        /*
         * Refresh all rows of librarians table
         */
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

    @FXML
    private void addLibrarian() throws IOException {
        /*
         * Show a new stage to add librarians
         */

        // initialize stage if it is null
        if (addLibrarianStage == null) {
            addLibrarianStage = new Stage();
            addLibrarianStage.initModality(Modality.APPLICATION_MODAL);
            var scene = new Scene(App.loadFXML("add_librarian"));
            new JMetro().setScene(scene);
            addLibrarianStage.setScene(scene);
        }

        addLibrarianStage.show();
    }
}
