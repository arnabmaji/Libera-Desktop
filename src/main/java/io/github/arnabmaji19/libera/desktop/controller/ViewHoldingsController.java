package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.model.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewHoldingsController implements Initializable {
    @FXML
    private Label titleTextField;
    @FXML
    private Label authorPublisherTextField;
    @FXML
    private ListView<Integer> availableHoldingsListView;
    @FXML
    private ListView<Integer> issuedHoldingsListView;

    private Book book;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // update title text view for current book
        titleTextField.setText(book.getTitle());
        var authorAndPublisherString = book.getAuthor() + ", " + book.getPublisher();
        authorPublisherTextField.setText(authorAndPublisherString);

    }

    public void init(Book book) {
        this.book = book;
    }
}
