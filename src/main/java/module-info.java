module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;  // Declare the dependency on JavaFX
    opens com.example.tictactoe to javafx.fxml; // Allow java fxml access
    exports com.example.tictactoe;  // Export your package
}