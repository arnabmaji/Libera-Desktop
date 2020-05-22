package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBooksController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<Author> authorComboBox;
    @FXML
    private ComboBox<Publisher> publisherComboBox;
    @FXML
    private TextField yearPublishedTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
