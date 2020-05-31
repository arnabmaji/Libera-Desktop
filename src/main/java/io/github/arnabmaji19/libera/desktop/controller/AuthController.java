package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.AuthRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import org.asynchttpclient.util.HttpConstants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Button authToggleButton;

    private AlertDialog alertDialog;
    private AuthRequest.AuthType activeAuthType;
    private JMetro jMetro;
    private Stage signUpStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertDialog = new AlertDialog();
        activeAuthType = AuthRequest.AuthType.LIBRARIAN;
        jMetro = new JMetro();
    }

    @FXML
    private void authenticate() {
        /*
         * Get email and password from text fields and validate them
         * On successful validation make auth request
         * If User is Authenticated show respective panel
         */

        var email = emailTextField.getText();
        var password = passwordField.getText();

        if (email.isBlank() || password.isBlank()) {
            alertDialog.show("All Fields are Required!");
            return;
        }

        authToggleButton.setDisable(true);
        progressbar.setVisible(true);

        // make http request to authenticate librarians
        AuthRequest
                .getInstance()
                .execute(activeAuthType, email, password)
                .thenAcceptAsync(response -> Platform.runLater(() -> {

                    authToggleButton.setDisable(false);
                    progressbar.setVisible(false);

                    if (response.getStatusCode() == HttpConstants.ResponseStatusCodes.OK_200) {


                        // create session
                        Session
                                .getInstance()
                                .create(response.getUserAuthDetails(), response.getAuthToken());

                        var stage = (Stage) emailTextField.getScene().getWindow();
                        stage.close();

                        try {

                            String fxml;
                            if (activeAuthType.equals(AuthRequest.AuthType.LIBRARIAN))
                                fxml = "librarian_panel";
                            else
                                fxml = "user_panel";

                            var scene = new Scene(App.loadFXML(fxml));
                            jMetro.setScene(scene);
                            stage.setScene(scene);
                            stage.setHeight(700);
                            stage.setWidth(1200);
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else alertDialog.show("Invalid Email or Password!");

                }));
    }

    @FXML
    private void toggleAuthType(ActionEvent e) {
        /*
         * Toggle Auth Type between Librarian and User
         */
        String buttonText;
        if (activeAuthType.equals(AuthRequest.AuthType.LIBRARIAN)) {
            activeAuthType = AuthRequest.AuthType.USER;
            buttonText = "Switch to Librarian Auth";
        } else {
            activeAuthType = AuthRequest.AuthType.LIBRARIAN;
            buttonText = "Switch to User Auth";
        }
        authToggleButton.setText(buttonText);
    }

    @FXML
    private void signUpAsUser() throws IOException {
        /*
         * Show Sign Up Stage
         */
        if (signUpStage == null) {
            var scene = new Scene(App.loadFXML("user_sign_up"));
            jMetro.setScene(scene);
            signUpStage = new Stage();
            signUpStage.setScene(scene);
            signUpStage.initModality(Modality.APPLICATION_MODAL);
        }

        signUpStage.show();
    }
}
