package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LibrarianPanelController {

    @FXML
    private Button addBooksButton;
    @FXML
    private Button searchBooksButton;
    @FXML
    private Pane pane;

    @FXML
    private void navigate(ActionEvent e) throws IOException {

        String fxml = null;

        if (e.getSource().equals(addBooksButton)) fxml = "add_books";
        else if (e.getSource().equals(searchBooksButton)) fxml = "search_books";

        pane.getChildren().setAll(App.loadFXML(fxml));
    }
}
