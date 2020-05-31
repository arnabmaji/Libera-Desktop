package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.StatisticsRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LibrarianDashboardController implements Initializable {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private Label booksCountLabel;
    @FXML
    private Label holdingsCountLabel;
    @FXML
    private Label authorsCountLabel;
    @FXML
    private Label publishersCountLabel;
    @FXML
    private Label issuesCountLabel;
    @FXML
    private Label usersCountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        updateDashboard();

    }

    private void updateDashboard() {
        /*
         * Make http requests to update all dashboard data
         */

        loadingImageView.setVisible(true);

        var statisticsRequest = StatisticsRequest.getInstance();
        var getBooksCount = statisticsRequest.getCount(StatisticsRequest.Entity.BOOKS);
        var getHoldingsCount = statisticsRequest.getCount(StatisticsRequest.Entity.HOLDINGS);
        var getAuthorsCount = statisticsRequest.getCount(StatisticsRequest.Entity.AUTHORS);
        var getPublishersCount = statisticsRequest.getCount(StatisticsRequest.Entity.PUBLISHERS);
        var getIssuesCount = statisticsRequest.getCount(StatisticsRequest.Entity.ISSUES);
        var getUsersCount = statisticsRequest.getCount(StatisticsRequest.Entity.USERS);

        CompletableFuture.allOf(
                getBooksCount,
                getHoldingsCount,
                getAuthorsCount,
                getPublishersCount,
                getIssuesCount,
                getUsersCount
        )
                .thenRunAsync(() -> Platform.runLater(() -> {

                    // update labels
                    try {
                        booksCountLabel.setText(String.valueOf(getBooksCount.get()));
                        holdingsCountLabel.setText(String.valueOf(getHoldingsCount.get()));
                        authorsCountLabel.setText(String.valueOf(getAuthorsCount.get()));
                        publishersCountLabel.setText(String.valueOf(getPublishersCount.get()));
                        issuesCountLabel.setText(String.valueOf(getIssuesCount.get()));
                        usersCountLabel.setText(String.valueOf(getUsersCount.get()));

                        loadingImageView.setVisible(false);

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                }));
    }
}
