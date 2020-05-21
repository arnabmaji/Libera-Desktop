package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianAuthController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ProgressBar progressbar;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertDialog = new AlertDialog();
    }

    @FXML
    private void authenticate() {
        /*
         * Get email and password from text fields and validate them
         * On successful validation make auth request
         */

        var email = emailTextField.getText();
        var password = passwordField.getText();

        if (email.isBlank() || password.isBlank()) {  //  if all fields are blank show error dialog
            alertDialog.show("All fields are required!");
            return;
        }

        // TODO: make http request to authenticate librarians
    }
}
