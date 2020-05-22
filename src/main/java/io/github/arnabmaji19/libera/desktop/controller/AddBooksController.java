package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.AuthorRequest;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.util.AutoCompleteComboBoxListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBooksController implements Initializable {

    @FXML
    private ComboBox<Author> authorComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AuthorRequest
                .getInstance()
                .get()
                .thenAcceptAsync(authors -> {
                    authorComboBox.setItems(FXCollections.observableArrayList(authors));
                    Platform.runLater(() -> new AutoCompleteComboBoxListener<Author>(authorComboBox));
                });
    }

}
