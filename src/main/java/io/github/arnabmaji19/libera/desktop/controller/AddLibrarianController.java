package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.LibrarianRequest;
import io.github.arnabmaji19.libera.desktop.model.Librarian;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddLibrarianController {
    @FXML
    private ImageView loadingImageView;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private CheckBox adminCheckBox;

    private AlertDialog alertDialog;

    @FXML
    private void add() {

        /*
         * Add new librarian in the database
         */

        // get all form parameters
        var firstName = firstNameTextField.getText();
        var lastName = lastNameTextField.getText();
        var email = emailTextField.getText();
        var password = passwordField.getText();
        var phone = phoneTextField.getText();
        var address = addressTextField.getText();

        // validate form parameters
        var errorMessage = Validations
                .validateFormParameters(firstName, lastName, email, password, phone, address);
        if (!errorMessage.isBlank()) {  // show error message if any
            if (alertDialog == null) alertDialog = new AlertDialog();
            alertDialog.show(errorMessage);
            return;
        }

        loadingImageView.setVisible(true);  // show the loading image

        var librarian = new Librarian(firstName, lastName,
                email, password,
                phone, address,
                adminCheckBox.isSelected() ? 1 : 0);

        // make an http post request to create new librarian
        LibrarianRequest
                .getInstance()
                .add(librarian)
                .thenAcceptAsync(success -> Platform.runLater(() -> {

                    loadingImageView.setVisible(false);  // hide the loading image

                    String message;
                    if (success) {
                        message = "Successful!";
                        // clear all form fields
                        firstNameTextField.clear();
                        lastNameTextField.clear();
                        emailTextField.clear();
                        passwordField.clear();
                        phoneTextField.clear();
                        addressTextField.clear();
                        adminCheckBox.setSelected(false);
                    } else message = "Something went wrong!";

                    if (alertDialog == null) alertDialog = new AlertDialog();
                    alertDialog.show(message);
                }));

    }
}
