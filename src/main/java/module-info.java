module io.github.arnabmaji19.libera.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jfxtras.styles.jmetro;

    opens io.github.arnabmaji19.libera.desktop to javafx.fxml;
    opens io.github.arnabmaji19.libera.desktop.controller to javafx.fxml;
    exports io.github.arnabmaji19.libera.desktop;
    exports io.github.arnabmaji19.libera.desktop.controller;
    exports io.github.arnabmaji19.libera.desktop.util;
}
