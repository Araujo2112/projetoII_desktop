module com.example.frontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.example.frontendjavafx to javafx.fxml;
    exports com.example.frontendjavafx;

    opens com.example.frontendjavafx.model to com.google.gson, com.fasterxml.jackson.databind, javafx.base;
    opens com.example.frontendjavafx.controllers to javafx.fxml;
    opens com.example.frontendjavafx.utils to javafx.fxml;
    opens com.example.frontendjavafx.controllers.gestor to javafx.fxml;
    opens com.example.frontendjavafx.controllers.manutencao to javafx.fxml;

    opens com.example.frontendjavafx.dto to com.google.gson, com.fasterxml.jackson.databind;

}
