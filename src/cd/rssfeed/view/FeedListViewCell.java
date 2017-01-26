package cd.rssfeed.view;

import cd.rssfeed.model.Feed;
import javafx.scene.control.ListCell;

public class FeedListViewCell extends ListCell<Feed> {
	@Override
	public void updateItem(Feed feed, boolean empty) {
		super.updateItem(feed, empty);

		if (feed != null)
		{
			FeedCell feedCell = new FeedCell();

			// Set Data
			feedCell.setInfo(feed.getFeedName(), feed.getFeedDescription(), feed.getFeedURL());
			// Set Graphics
			setGraphic(feedCell.getBox());
		}
		else
			setGraphic(null);
	}
}
