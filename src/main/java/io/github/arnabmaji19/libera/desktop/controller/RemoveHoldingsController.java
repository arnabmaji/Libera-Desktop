package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.HoldingRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveHoldingsController implements Initializable {

    @FXML
    private TextField holdingNumberTextField;
    @FXML
    private ImageView loadingImageView;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.alertDialog = new AlertDialog();
    }

    @FXML
    private void remove() {

        var number = holdingNumberTextField.getText();

        if (!Validations.isNumber(number)) {
            alertDialog.show("Holding Number must be an Integer!");
            return;
        }

        // make an http request to remove the holding
        loadingImageView.setVisible(true);
        HoldingRequest
                .getInstance()
                .remove(Integer.parseInt(number))
                .thenAcceptAsync(success -> Platform.runLater(() -> {
                    alertDialog.show(success ? "Successful!" : "Something went wrong!");
                    loadingImageView.setVisible(false);
                }));
    }
}
