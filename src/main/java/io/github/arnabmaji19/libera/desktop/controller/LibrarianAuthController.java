package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.AuthRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Session;
import javafx.application.Platform;
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

        progressbar.setVisible(true);  // show the progress bar

        // make http request to authenticate librarians
        AuthRequest
                .getInstance()
                .execute(AuthRequest.AuthType.LIBRARIAN, email, password)
                .thenAcceptAsync(response -> {

                    String message;
                    if (response.getStatusCode() == 200) {
                        // user if successfully authenticated
                        message = "Successful";
                        // create session
                        Session
                                .getInstance()
                                .create(response.getUser(), response.getAuthToken());
                    } else {
                        message = "Invalid email or password!";
                    }

                    // run ui changes on fx application thread
                    Platform.runLater(() -> {
                        progressbar.setVisible(false);
                        alertDialog.show(message);
                    });

                });
    }
}
