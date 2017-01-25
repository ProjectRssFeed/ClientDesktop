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
			feedSourceCell.setInfo(feedSource.getFeedName(), feedSource.getFeedDescription(), feedSource.getFeedURL());
			// Set Graphics
			setGraphic(feedSourceCell.getBox());
		}
		else
			setGraphic(null);
	}
}
