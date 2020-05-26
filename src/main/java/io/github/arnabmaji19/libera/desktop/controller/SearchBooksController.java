package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import io.github.arnabmaji19.libera.desktop.datasource.BookRequest;
import io.github.arnabmaji19.libera.desktop.model.Book;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;

import java.io.IOException;
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

        // restrict to select only one row at a time
        booksTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

    @FXML
    private void showHoldings() throws IOException {
        /*
         * Show a stage with all available and issued holdings for a book
         */
        var book = booksTableView.getSelectionModel().getSelectedItem();
        if (book == null) {
            alertDialog.show("Please Select a Book First!");
            return;
        }

        var controller = new ViewHoldingsController();
        controller.init(book);
        var fxmlLoader = App.getFXMLLoader("view_holdings");
        fxmlLoader.setController(controller);
        var parent = fxmlLoader.load();
        var scene = new Scene((Parent) parent);
        new JMetro().setScene(scene);
        var stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
