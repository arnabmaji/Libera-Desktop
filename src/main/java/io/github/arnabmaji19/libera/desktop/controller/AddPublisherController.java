package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.PublisherRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPublisherController implements Initializable {

    @FXML
    private TextField nameTextField;
    @FXML
    private ImageView loadingImageView;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.alertDialog = new AlertDialog();
    }

    @FXML
    private void add() {
        /*
         * Add new publisher
         */

        var name = nameTextField.getText();

        if (name.isBlank()) {
            alertDialog.show("Name can't be Blank!");
            return;
        }

        loadingImageView.setVisible(true);  // show loading animation

        // make an http request to create new author
        PublisherRequest
                .getInstance()
                .add(name)
                .thenAcceptAsync(success -> Platform.runLater(() -> {
                    loadingImageView.setVisible(false);
                    String message;
                    if (success) {
                        message = "Successful!";
                        // clear all form fields
                        nameTextField.clear();
                    } else message = "Something went wrong!";
                    alertDialog.show(message);  // show response
                }));
    }
}
