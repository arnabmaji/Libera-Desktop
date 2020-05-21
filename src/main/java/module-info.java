module io.github.arnabmaji19.libera.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jfxtras.styles.jmetro;
    requires async.http.client;
    requires com.google.gson;

    opens io.github.arnabmaji19.libera.desktop to javafx.fxml;
    opens io.github.arnabmaji19.libera.desktop.controller to javafx.fxml;
    exports io.github.arnabmaji19.libera.desktop;
    exports io.github.arnabmaji19.libera.desktop.controller;
    exports io.github.arnabmaji19.libera.desktop.util;
    exports io.github.arnabmaji19.libera.desktop.datasource;
    exports io.github.arnabmaji19.libera.desktop.model;
}
