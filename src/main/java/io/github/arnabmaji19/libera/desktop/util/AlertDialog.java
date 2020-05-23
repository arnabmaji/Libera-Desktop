package io.github.arnabmaji19.libera.desktop.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class AlertDialog {

    private final FlatAlert alert;

    public AlertDialog() {
        JMetro jMetro = new JMetro(Style.LIGHT);
        this.alert = new FlatAlert(Alert.AlertType.NONE);
        this.alert.getButtonTypes().setAll(ButtonType.OK);
        jMetro.setScene(alert.getDialogPane().getScene());
        ((Stage) alert.getDialogPane().getScene().getWindow()).initStyle(StageStyle.UNDECORATED);
    }

    public void show(String message) {
        alert.setContentText(message);
        alert.showAndWait();
    }
}
