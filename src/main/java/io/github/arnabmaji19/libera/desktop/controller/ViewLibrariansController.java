package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.LibrarianRequest;
import io.github.arnabmaji19.libera.desktop.model.LibrarianDetails;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private AlertDialog alertDialog;
    private Alert confirmationAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // restrict to select only one row at a time
        librariansTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.alertDialog = new AlertDialog();
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

    @FXML
    private void deleteLibrarian() {

        var librarian = librariansTableView.getSelectionModel().getSelectedItem();

        if (librarian == null) {
            alertDialog.show("Please Select a Row First!");
            return;
        }

        if (confirmationAlert == null) {
            confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setHeaderText("");
            var scene = confirmationAlert.getDialogPane().getScene();
            new JMetro().setScene(scene);
            ((Stage) scene.getWindow()).initStyle(StageStyle.UNDECORATED);
        }

        // show confirmation dialog
        confirmationAlert.setContentText("Do You Want to Delete\n" + librarian.getEmail() + " ?");
        var selectedButton = confirmationAlert.showAndWait();
        if (selectedButton.isEmpty() || selectedButton.get().equals(ButtonType.CANCEL)) return;

        loadingImageView.setVisible(true);  // show the loading image view

        // make an http request to delete librarian
        LibrarianRequest
                .getInstance()
                .delete(librarian.getId())
                .thenAcceptAsync(success -> Platform.runLater(() -> {
                    loadingImageView.setVisible(false);  // hide the loading image
                    String message;
                    if (success) {
                        message = "Successful!";
                        refreshTableData();  // refresh table data
                    } else message = "Something Went Wrong!";
                    alertDialog.show(message);
                }));
    }
}
