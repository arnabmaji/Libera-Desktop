package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.IssueRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnBooksController implements Initializable {
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
    private void returnHolding() {

        var holdingNumber = holdingNumberTextField.getText();
        if (!Validations.isNumber(holdingNumber)) {
            alertDialog.show("Holding Number must be an Integer!");
            return;
        }

        loadingImageView.setVisible(true);

        // make an http request to return current holding
        IssueRequest
                .getInstance()
                .returnHolding(Integer.parseInt(holdingNumber))
                .thenAcceptAsync(success -> Platform.runLater(() -> {
                    loadingImageView.setVisible(false);
                    String message;
                    if (success) {
                        message = "Successful!";
                        holdingNumberTextField.clear();
                    } else message = "Something went wrong!";
                    alertDialog.show(message);
                }));

    }
}
