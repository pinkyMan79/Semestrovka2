module Semestrovka2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires lombok;

    opens com.example.semestrovka2 to javafx.fxml;
    exports com.example.semestrovka2.client.GUI;
}//--module-path "/home/lino/Documents/javafx-sdk-19/lib" --add-modules javafx.controls,javafx.fxml