package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.HoldingRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddHoldingsController implements Initializable {

    @FXML
    private TextField bookIdTextField;
    @FXML
    private Spinner<Integer> holdingQuantitySpinner;
    @FXML
    private ListView<Integer> holdingsListView;
    @FXML
    private VBox holdingsVBox;
    @FXML
    private ImageView loadingImageView;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertDialog = new AlertDialog();
    }

    @FXML
    private void generate() {

        // validate params
        String bookId = bookIdTextField.getText();
        int quantity = holdingQuantitySpinner.getValue();

        if (!Validations.isNumber(bookId)) {
            alertDialog.show("Book ID must be a Number!");
            return;
        }

        loadingImageView.setVisible(true);  // show the loading animation
        HoldingRequest
                .getInstance()
                .generate(Integer.parseInt(bookId), quantity)
                .thenAccept(holdingsNumbers -> Platform.runLater(() -> {
                    // update items for list view
                    holdingsListView.setItems(FXCollections.observableArrayList(holdingsNumbers));
                    holdingsVBox.setVisible(true);
                    loadingImageView.setVisible(false);  // hide the loading animation
                }));
    }

    @FXML
    private void exportToFile() {
        // TODO: export all generate holdings to a file
    }
}
