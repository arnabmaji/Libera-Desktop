package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.AuthorRequest;
import io.github.arnabmaji19.libera.desktop.datasource.BookRequest;
import io.github.arnabmaji19.libera.desktop.datasource.PublisherRequest;
import io.github.arnabmaji19.libera.desktop.model.Author;
import io.github.arnabmaji19.libera.desktop.model.Publisher;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.AutoCompleteComboBoxListener;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;

import java.io.IOException;
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
    @FXML
    private ImageView loadingImageView;

    private AlertDialog alertDialog;
    private Stage stage;
    private JMetro jMetro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.alertDialog = new AlertDialog();
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setOnHiding(windowEvent -> updateComboboxSuggestions());
        this.jMetro = new JMetro();

        updateComboboxSuggestions();

    }

    @FXML
    private void addNewBook() {

        var title = titleTextField.getText();
        var author = authorComboBox.getValue();
        var publisher = publisherComboBox.getValue();
        var yearPublished = yearPublishedTextField.getText();

        var errorMessage = validateFields(title, author, publisher, yearPublished);
        if (!errorMessage.isEmpty()) {
            alertDialog.show(errorMessage);
            return;
        }

        loadingImageView.setVisible(true);  // show the loading animation

        // make an http request to add new book
        BookRequest
                .getInstance()
                .add(title, author, publisher, yearPublished)
                .thenAccept(successful -> Platform.runLater(() -> {
                    loadingImageView.setVisible(false);  // hide the loading animation
                    String message;
                    if (successful) {
                        message = "Successful!";

                        // clear all form fields
                        titleTextField.clear();
                        authorComboBox.getSelectionModel().clearSelection();
                        publisherComboBox.getSelectionModel().clearSelection();
                        yearPublishedTextField.clear();
                    } else message = "Oops! Something went wrong!";
                    alertDialog.show(message);
                }));
    }

    private String validateFields(String title,
                                  Author author,
                                  Publisher publisher,
                                  String yearPublished) {
        /*
         * Validate Book Fields
         */

        if (title.isBlank()) return "Title can't be blank!";
        if (author == null) return "Please select a valid Author!";
        if (publisher == null) return "Please select a valid Publisher!";
        if (!Validations.isNumber(yearPublished)) return "Year can't be a String!";
        return "";
    }


    @FXML
    private void addAuthor() throws IOException {
        /*
         * Show a stage to add new authors
         */

        show("add_author");

    }

    @FXML
    private void addPublisher() throws IOException {
        /*
         * Show a stage to add new publishers
         */

        show("add_publisher");
    }

    private void show(String fxml) throws IOException {
        var scene = new Scene(App.loadFXML(fxml));
        jMetro.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void updateComboboxSuggestions() {

        /*
         * Update suggestion List for author and publisher combobox
         */

        // fetch authors for combobox
        var fetchAuthors = AuthorRequest
                .getInstance()
                .get()
                .thenAcceptAsync(authors -> authorComboBox
                        .setItems(FXCollections.observableArrayList(authors))
                );

        // fetch publishers for combobox
        var fetchPublishers = PublisherRequest
                .getInstance()
                .get()
                .thenAcceptAsync(publishers -> publisherComboBox
                        .setItems(FXCollections.observableArrayList(publishers))
                );

        // populate author and publisher combobox with data
        CompletableFuture
                .allOf(fetchAuthors, fetchPublishers)
                .thenRun(() -> Platform.runLater(() -> {
                    new AutoCompleteComboBoxListener<Author>(authorComboBox);
                    new AutoCompleteComboBoxListener<Publisher>(publisherComboBox);
                }));
    }
}
