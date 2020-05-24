package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class IssueBooksController implements Initializable {

    @FXML
    private TextField emailTextField;
    @FXML
    private ImageView loadingImageView;
    @FXML
    private VBox userDetailsForm;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private VBox addHoldingForm;
    @FXML
    private TextField holdingNumberTextField;
    @FXML
    private ListView<Integer> addedHoldingsListView;
    @FXML
    private ImageView checkoutLoadingAnimation;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.alertDialog = new AlertDialog();
    }

    @FXML
    private void addHoldingToList() {
        /*
         * Add a holding to the cart
         */
        var holdingNumber = holdingNumberTextField.getText();

        if (!Validations.isNumber(holdingNumber)) {
            alertDialog.show("Holding Number must be an Integer!");
            return;
        }
        addedHoldingsListView.getItems().add(Integer.parseInt(holdingNumber));
        holdingNumberTextField.clear();
    }

    @FXML
    private void fetchUser() {
        /*
         * Fetch user by email and show user details in the form
         */

        var email = emailTextField.getText();
        if (email.isBlank()) {
            alertDialog.show("Email can't be Empty!");
            return;
        }


    }
}
