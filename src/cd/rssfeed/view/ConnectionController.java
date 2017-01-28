package cd.rssfeed.view;

import cd.rssfeed.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnectionController {
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	private MainApp mainApp;


    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    @FXML
    private void handleConnection() {
    	mainApp.showFeedOverview();
    }

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
