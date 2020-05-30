package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianPanelController implements Initializable {

    @FXML
    private Button dashboardButton;
    @FXML
    private Button searchBooksButton;
    @FXML
    private Button addHoldingsButton;
    @FXML
    private Button removeHoldingsButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button addBooksButton;
    @FXML
    private Button issueBooksButton;
    @FXML
    private Button returnBooksButton;
    @FXML
    private Button viewLibrariansButton;
    @FXML
    private Button viewUsersButton;

    private Button activeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // show dashboard panel by default
        try {
            showPanel(dashboardButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        if (activeButton.equals(dashboardButton)) fxml = "librarian_dashboard";
        else if (activeButton.equals(addBooksButton)) fxml = "add_books";
        else if (activeButton.equals(searchBooksButton)) fxml = "search_books";
        else if (activeButton.equals(addHoldingsButton)) fxml = "add_holdings";
        else if (activeButton.equals(removeHoldingsButton)) fxml = "remove_holdings";
        else if (activeButton.equals(issueBooksButton)) fxml = "issue_books";
        else if (activeButton.equals(returnBooksButton)) fxml = "return_books";
        else if (activeButton.equals(viewLibrariansButton)) fxml = "view_librarians";
        else if (activeButton.equals(viewUsersButton)) fxml = "view_users";

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
