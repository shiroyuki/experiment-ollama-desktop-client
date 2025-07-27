module com.shiroyuki.hallo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires static lombok;

    opens com.shiroyuki.hallo to javafx.fxml;
    exports com.shiroyuki.hallo;
}