package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.BookRequest;
import io.github.arnabmaji19.libera.desktop.model.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class ViewHoldingsController implements Initializable {
    @FXML
    private Label titleTextField;
    @FXML
    private Label authorPublisherTextField;
    @FXML
    private ListView<Integer> availableHoldingsListView;
    @FXML
    private ListView<Integer> issuedHoldingsListView;
    @FXML
    private ImageView loadingImageView;

    private Book book;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // update title text view for current book
        titleTextField.setText(book.getTitle());
        var authorAndPublisherString = book.getAuthor() + ", " + book.getPublisher();
        authorPublisherTextField.setText(authorAndPublisherString);

        loadingImageView.setVisible(true);  // show loading animation

        // fetch available holdings for a book
        var getAvailableHoldings = BookRequest
                .getInstance()
                .getHoldings(BookRequest.ListType.AVAILABLE, book.getId())
                .thenAcceptAsync(list -> Platform.runLater(() -> availableHoldingsListView.setItems(FXCollections.observableArrayList(list))));

        // fetch all issued holdings for a book
        var getIssuedHoldings = BookRequest
                .getInstance()
                .getHoldings(BookRequest.ListType.ISSUED, book.getId())
                .thenAcceptAsync(list -> Platform.runLater(() -> issuedHoldingsListView.setItems(FXCollections.observableArrayList(list))));


        // hide the loading animation on completion of both requests
        CompletableFuture.allOf(getAvailableHoldings, getIssuedHoldings)
                .thenRun(() -> Platform.runLater(() -> loadingImageView.setVisible(false)));

    }

    public void init(Book book) {
        this.book = book;
    }
}
