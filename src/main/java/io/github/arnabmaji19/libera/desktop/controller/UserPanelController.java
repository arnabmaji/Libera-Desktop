package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserPanelController {

    @FXML
    private Button searchBooksButton;
    @FXML
    private Button issuedBooksButton;
    @FXML
    private Button readingHistoryButton;
    @FXML
    private AnchorPane pane;

    private Button activeButton;

    @FXML
    private void navigate(ActionEvent e) throws IOException {
        /*
         * Navigate to the Selected Panel
         */

        if (activeButton != null)
            activeButton.getStyleClass().remove("selected-button");

        activeButton = (Button) e.getSource();
        showPanel(activeButton);
        activeButton.getStyleClass().add("selected-button");
    }

    private void showPanel(Button activeButton) throws IOException {

        String fxml = null;
        if (activeButton.equals(searchBooksButton)) fxml = "search_books";
        else if (activeButton.equals(issuedBooksButton)) fxml = "user_issues";

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
