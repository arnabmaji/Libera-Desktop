package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.UserRequest;
import io.github.arnabmaji19.libera.desktop.model.User;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;

import java.io.IOException;

public class ViewUsersController {
    @FXML
    private ImageView loadingImageView;
    @FXML
    private TableView<User> usersTableView;

    private AlertDialog alertDialog;
    private Stage viewIssuesStage;
    private JMetro jMetro;

    @FXML
    private void refreshTableData() {
        /*
         * Refresh table data
         */

        loadingImageView.setVisible(true);

        // make an http request to get all user details
        UserRequest
                .getInstance()
                .get()
                .thenAcceptAsync(users -> Platform.runLater(() -> {
                    usersTableView.setItems(FXCollections.observableArrayList(users));
                    loadingImageView.setVisible(false);
                }));

    }

    @FXML
    private void viewIssues() throws IOException {
        /*
         * View Issued Books for a particular customer
         */

        var selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            if (alertDialog == null) alertDialog = new AlertDialog();
            alertDialog.show("Please Select a User First!");
            return;
        }

        var controller = new ViewIssuesController();
        controller.init(selectedUser);

        var loader = App.getFXMLLoader("view_issues");
        loader.setController(controller);

        var scene = new Scene(loader.load());
        if (jMetro == null) jMetro = new JMetro();
        jMetro.setScene(scene);

        if (viewIssuesStage == null) {
            viewIssuesStage = new Stage();
            viewIssuesStage.initModality(Modality.APPLICATION_MODAL);
        }
        viewIssuesStage.setScene(scene);
        viewIssuesStage.show();

    }
}
