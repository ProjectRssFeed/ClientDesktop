package cd.rssfeed.view;

import cd.rssfeed.model.FeedSource;
import javafx.scene.control.ListCell;

public class FeedSourceListViewCell extends ListCell<FeedSource> {
	@Override
	public void updateItem(FeedSource feedSource, boolean empty) {
		super.updateItem(feedSource, empty);

		if (feedSource != null)
		{
			FeedSourceCell feedSourceCell = new FeedSourceCell();

			// Set Data
			feedSourceCell.setInfo(feedSource.getFeedSourceName(), feedSource.getFeedSourceDescription(), feedSource.getFeedSourceURL());
			// Set Graphics
			setGraphic(feedSourceCell.getBox());
		}
		else
			setGraphic(null);
	}
}
