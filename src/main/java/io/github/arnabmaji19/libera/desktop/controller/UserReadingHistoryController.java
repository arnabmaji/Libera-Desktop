package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.UserRequest;
import io.github.arnabmaji19.libera.desktop.model.Book;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserReadingHistoryController implements Initializable {

    public ImageView loadingImageView;
    public TableView<Book> booksTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadingImageView.setVisible(true);

        // make an http request to fetch user's reading history
        UserRequest
                .getInstance()
                .getReadingHistory()
                .thenAcceptAsync(books -> Platform.runLater(() -> {

                    booksTableView.setItems(FXCollections.observableArrayList(books));
                    loadingImageView.setVisible(false);

                }));
    }
}
