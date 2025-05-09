module com.example.frontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    opens com.example.frontendjavafx to javafx.fxml;
    exports com.example.frontendjavafx;

    opens com.example.frontendjavafx.model to com.google.gson, javafx.base;
    opens com.example.frontendjavafx.controllers to javafx.fxml;
    opens com.example.frontendjavafx.utils to javafx.fxml;
    opens com.example.frontendjavafx.controllers.gestor to javafx.fxml;

    // ✅ Esta linha é a que resolve o teu problema:
    opens com.example.frontendjavafx.dto to com.google.gson;
}
