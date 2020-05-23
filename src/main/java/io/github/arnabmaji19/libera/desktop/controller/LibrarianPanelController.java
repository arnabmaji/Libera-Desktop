package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LibrarianPanelController {

    @FXML
    private Button addBooksButton;
    @FXML
    private Button searchBooksButton;
    @FXML
    private AnchorPane pane;

    @FXML
    private void navigate(ActionEvent e) throws IOException {

        String fxml = null;

        if (e.getSource().equals(addBooksButton)) fxml = "add_books";
        else if (e.getSource().equals(searchBooksButton)) fxml = "search_books";

        var child = App.loadFXML(fxml);
        // set anchors for the child
        AnchorPane.setTopAnchor(child, 0.0);
        AnchorPane.setBottomAnchor(child, 0.0);
        AnchorPane.setLeftAnchor(child, 0.0);
        AnchorPane.setRightAnchor(child, 0.0);
        // add child to the pane
        pane.getChildren().setAll(child);
    }
}
