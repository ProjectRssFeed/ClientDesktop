package cd.rssfeed.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import javafx.scene.control.ListView;
import cd.rssfeed.MainApp;
import cd.rssfeed.model.FeedSource;


public class FeedOverviewController {
	@FXML
	private ListView<FeedSource> feedSourceList;

	private MainApp mainApp;

	public FeedOverviewController() {

	}

	@FXML
	private void initialize() {

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewFeedSource() {
	    FeedSource tempFeedSource= new FeedSource();
	    boolean okClicked = mainApp.showFeedSourceAddDialog(tempFeedSource);
	    if (okClicked) {
	        mainApp.getFeedSourceData().add(tempFeedSource);
	    }
	}

	@FXML
	private void handleRemoveFeedSource() {
		int selectedIndex = feedSourceList.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			feedSourceList.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("No Selection");
	        alert.setHeaderText("No Feed Source Selected");
	        alert.setContentText("Please select a feed source in the list.");

	        alert.showAndWait();
	    }
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		ObservableList<FeedSource> feedList = mainApp.getFeedSourceData();
		if (feedList != null)
			feedSourceList.setItems(feedList);
		feedSourceList.setCellFactory(new Callback<ListView<FeedSource>, javafx.scene.control.ListCell<FeedSource>>()
        {
            @Override
            public ListCell<FeedSource> call(ListView<FeedSource> listView)
            {
                return new FeedSourceListViewCell();
            }
        });
	}
}
