package cd.rssfeed.view;

import javafx.fxml.FXML;

import java.util.ArrayList;

import cd.rssfeed.MainApp;

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Called when the user clicks Change Server.
     */
    @FXML
    private void handleSettings() {
    	ArrayList<String> conf = new ArrayList<String>();

    	conf.add(mainApp.getConfiguration().getServerURL());
    	conf.add(mainApp.getConfiguration().getApiVersion());

    	boolean okClicked = mainApp.showSettingsDialog(conf);
	    if (okClicked) {
	    	mainApp.setConfiguration(conf);
	    }

    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}