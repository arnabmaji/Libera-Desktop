package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.AuthorRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAuthorController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
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
         * Add a new Author in the database
         */

        var firstName = firstNameTextField.getText();
        var lastName = lastNameTextField.getText();

        // validate params
        if (firstName.isBlank() ||
                lastName.isBlank()) {
            alertDialog.show("All Fields are Required!");
            return;
        }

        loadingImageView.setVisible(true);  // show the loading animation

        // make an http request to add new author
        AuthorRequest
                .getInstance()
                .add(firstName, lastName)
                .thenAcceptAsync(success -> Platform.runLater(() -> {
                    loadingImageView.setVisible(false);
                    String message;
                    if (success) {
                        message = "Successful!";
                        // clear all form fields
                        firstNameTextField.clear();
                        lastNameTextField.clear();
                    } else message = "Something went wrong!";
                    alertDialog.show(message);  // show response
                }));

    }
}
