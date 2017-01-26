package cd.rssfeed.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import cd.rssfeed.model.FeedSource;

public class FeedSourceAddDialogController {
	@FXML
	private TextField titleField;
	@FXML
	private TextField descriptionField;
	@FXML
	private TextField urlField;


	private Stage dialogStage;
	private FeedSource feedSource;
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


    public void setFeedSource(FeedSource feedSource) {
        this.feedSource = feedSource;
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
//            feedSource.setFeedName(titleField.getText());
//            feedSource.setFeedDescription(descriptionField.getText());
            feedSource.setFeedSourceURL(urlField.getText());

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

        if (urlField.getText() == null || urlField.getText().length() == 0) {
            errorMessage += "Invalid URL!\n";
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
