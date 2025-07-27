package com.shiroyuki.ollama_desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private final List<String> logList = new ArrayList<>();

    private String altKeyHoldingLocation = null;

    @FXML
    public VBox logs;

    @FXML
    protected TextField apiUri = new TextField("http://127.0.0.1:11434/");

    @FXML
    private Label status;

    @FXML
    private TextArea prompt;

    @FXML
    private void onKeyPressed(KeyEvent event) {
        final KeyCode code = event.getCode();
        //System.out.printf("PANDA: O: %s\n", code);

        if (code == KeyCode.ALT || code == KeyCode.ALT_GRAPH) {
            altKeyHoldingLocation = "prompt";
            return;
        }

        if (code == KeyCode.ENTER && altKeyHoldingLocation != null && altKeyHoldingLocation.equals("prompt")) {
            updateContext();
            altKeyHoldingLocation = null;
        }
    }

    @FXML
    private void onKeyReleased(KeyEvent event) {
        final KeyCode code = event.getCode();
        //System.out.printf("PANDA: X: %s\n", code);

        if (code == KeyCode.ALT || code == KeyCode.ALT_GRAPH) {
            if (altKeyHoldingLocation != null) {
                altKeyHoldingLocation = null;

                return;
            }
        }
    }

    @FXML
    public void onPromptSubmit(ActionEvent event) {
        updateContext();
    }

    protected void updateContext() {
        logList.add(prompt.getText());

        status.setText("In progress...");
        prompt.setDisable(true);

        VBox.setVgrow(logs, Priority.ALWAYS);
        logs.getChildren().clear();
        logs.getChildren()
                .addAll(
                        logList.stream()
                                .map(text -> {
                                    var actor = new Label("You");
                                    actor.setStyle("-fx-font-weight: 700; -fx-text-fill: white; -fx-background-color: #222; -fx-padding: 4px 8px;");

                                    var loggedPrompt = new Label(text);
                                    loggedPrompt.setMaxWidth(Double.MAX_VALUE);
                                    loggedPrompt.setMinHeight(100);
                                    loggedPrompt.setWrapText(true);
                                    loggedPrompt.setStyle("-fx-background-color: #eee; -fx-padding: 4px 8px;");

                                    return List.of(actor, loggedPrompt);
                                })
                                .flatMap(List::stream)
                                .toList()
                );

        prompt.setDisable(false);
        prompt.clear();

        status.setText("✅ Updated");
    }
}