package cd.rssfeed.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ConfigurationController {
	@FXML
	private TextField serverUrlField;
	@FXML
	private TextField apiVersionField;


	private Stage dialogStage;
	private ArrayList<String> conf;
	private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setConfiguration(ArrayList<String> conf) {
        this.conf = conf;
        serverUrlField.setText(conf.get(0));
        apiVersionField.setText(conf.get(1));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	conf.clear();
        	conf.add(serverUrlField.getText());
        	conf.add(apiVersionField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (serverUrlField.getText() == null || serverUrlField.getText().length() == 0) {
            errorMessage += "Invalid URL!\n";
        }
        if (apiVersionField.getText() == null || apiVersionField.getText().length() == 0) {
            errorMessage += "Invalid Api Version!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
