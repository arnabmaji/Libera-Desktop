package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.AuthorRequest;
import io.github.arnabmaji19.libera.desktop.datasource.PublisherRequest;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import io.github.arnabmaji19.libera.desktop.util.AutoCompleteComboBoxListener;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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

        // fetch authors for combobox
        var fetchAuthors = AuthorRequest
                .getInstance()
                .get()
                .thenAcceptAsync(authors -> authorComboBox.setItems(FXCollections.observableArrayList(authors)));

        // fetch publishers for combobox
        var fetchPublishers = PublisherRequest
                .getInstance()
                .get()
                .thenAcceptAsync(publishers -> publisherComboBox.setItems(FXCollections.observableArrayList(publishers)));


        CompletableFuture
                .allOf(fetchAuthors, fetchPublishers)
                .thenRun(() -> Platform.runLater(() -> {
                    new AutoCompleteComboBoxListener<Author>(authorComboBox);
                    new AutoCompleteComboBoxListener<Publisher>(publisherComboBox);
                }));

    }

    @FXML
    private void addNewBook() {

        var title = titleTextField.getText();
        var author = authorComboBox.getValue();
        var publisher = publisherComboBox.getValue();
        var yearPublished = yearPublishedTextField.getText();

        if (title.isBlank() ||
                author == null ||
                publisher == null ||
                !Validations.isNumber(yearPublished)) {
            System.out.println("ERROR");
        }
    }


}
