package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.BookRequest;
import io.github.arnabmaji19.libera.desktop.model.Book;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBooksController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private TextField searchStringTextField;
    @FXML
    private TableView<Book> booksTableView;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertDialog = new AlertDialog();
    }

    @FXML
    private void search() {

        // get keyword from search field
        var keyword = searchStringTextField.getText();
        if (keyword.isEmpty()) {
            alertDialog.show("Search Field can't be empty!");
            return;
        }

        // show loading animation
        loadingImageView.setVisible(true);
        // load all matched books
        BookRequest
                .getInstance()
                .search(keyword)
                .thenAccept(books -> {
                    booksTableView.getItems().setAll(books);  // bind books with table
                    Platform.runLater(() -> loadingImageView.setVisible(false));  // hide the loading animation
                });
    }
}
