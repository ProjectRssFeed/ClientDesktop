package cd.rssfeed.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import javafx.scene.control.ListView;
import cd.rssfeed.MainApp;
import cd.rssfeed.model.Feed;
import cd.rssfeed.model.FeedSource;


public class FeedOverviewController {
	@FXML
	private ListView<FeedSource> feedSourceList;
	@FXML
	private ListView<Feed> feedList;

	private MainApp mainApp;

	public FeedOverviewController() {

	}

	@FXML
	private void initialize() {

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to add
	 * a new FeedSource
	 */
	@FXML
	private void handleNewFeedSource() {
	    FeedSource newSource= new FeedSource();
	    boolean okClicked = mainApp.showFeedSourceAddDialog(newSource);
	    if (okClicked) {
	    	mainApp.addNewFeedSource(newSource);
	    }
	}

	/**
	 * Called when the user clicks the remove button. Remove a FeedSource
	 * Show an alert Dialog if nothing selected
	 */
	@FXML
	private void handleRemoveFeedSource() {
		int selectedIndex = feedSourceList.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			FeedSource removedSource = feedSourceList.getItems().get(selectedIndex);
			mainApp.removeFeedSource(removedSource);
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

	/**
	 * Called when the user clicks the Refresh button below FeedList
	 * Reload FeedList view
	 */
	@FXML
	private void handleRefreshFeedList() {
		mainApp.reloadFeedList();
	}

	/**
	 * Called when the user clicks the Refresh button below FeedSource
	 * Reload FeedSource view
	 */
	@FXML
	private void handleRefreshFeedSource() {
		mainApp.loadFeedSource();
	}

	/**
	 * Custom FeedSource List Cell Factory
	 */
	private void configureFeedSourceList() {
		// Feed Source item building
		ObservableList<FeedSource> tmpFeedSourceList = mainApp.getFeedSourceData();
		if (tmpFeedSourceList != null)
			feedSourceList.setItems(tmpFeedSourceList);
		feedSourceList.setCellFactory(new Callback<ListView<FeedSource>, javafx.scene.control.ListCell<FeedSource>>()
        {
            @Override
            public ListCell<FeedSource> call(ListView<FeedSource> listView)
            {
                return new FeedSourceListViewCell();
            }
        });
	}

	/**
	 * Custom Feed List Cell Factory
	 */
	private void configureFeedList() {
		// Feed Source item building
		ObservableList<Feed> tmpFeedList = mainApp.getFeedData();
		if (tmpFeedList != null)
			feedList.setItems(tmpFeedList);
		feedList.setCellFactory(new Callback<ListView<Feed>, javafx.scene.control.ListCell<Feed>>()
        {
            @Override
            public ListCell<Feed> call(ListView<Feed> listView)
            {
                return new FeedListViewCell();
            }
        });
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		configureFeedSourceList();
		configureFeedList();
	}
}
