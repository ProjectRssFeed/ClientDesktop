package cd.rssfeed.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
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
