package io.github.arnabmaji19.libera.desktop.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jfxtras.styles.jmetro.FlatAlert;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class AlertDialog {

    private JMetro jMetro;
    private FlatAlert alert;

    public AlertDialog() {
        this.jMetro = new JMetro(Style.LIGHT);
        this.alert = new FlatAlert(Alert.AlertType.NONE);
        this.alert.getButtonTypes().setAll(ButtonType.OK);
        this.jMetro.setScene(alert.getDialogPane().getScene());
    }

    public void show(String message) {
        alert.setContentText(message);
        alert.showAndWait();
    }
}
