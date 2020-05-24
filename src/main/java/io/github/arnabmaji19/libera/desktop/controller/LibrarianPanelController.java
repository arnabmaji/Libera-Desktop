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
    private Button addHoldingsButton;
    @FXML
    private Button removeHoldingsButton;
    @FXML
    private AnchorPane pane;

    @FXML
    private void navigate(ActionEvent e) throws IOException {

        String fxml = null;
        var source = e.getSource();
        if (source.equals(addBooksButton)) fxml = "add_books";
        else if (source.equals(searchBooksButton)) fxml = "search_books";
        else if (source.equals(addHoldingsButton)) fxml = "add_holdings";
        else if (source.equals(removeHoldingsButton)) fxml = "remove_holdings";

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
