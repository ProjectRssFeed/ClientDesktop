package cd.rssfeed.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Feed {
	private final StringProperty feedName;
	private final StringProperty feedURL;
	private final StringProperty feedDescription;

	public Feed() {
		this(null, null, null);
	}

	public Feed(String feedName, String feedURL, String feedDescription) {
		this.feedName = new SimpleStringProperty(feedName);
		this.feedURL = new SimpleStringProperty(feedURL);
		this.feedDescription = new SimpleStringProperty(feedDescription);
	}

	public String getFeedName() {
		return feedName.get();
	}

	public void setFeedName(String feedName) {
		this.feedName.set(feedName);
	}

	public StringProperty feedNameProperty() {
		return feedName;
	}

	public String getFeedURL() {
		return feedURL.get();
	}

	public void setFeedURL(String feedURL) {
		this.feedURL.set(feedURL);
	}

	public StringProperty FeedURLProperty() {
		return feedURL;
	}

	public String getFeedDescription() {
		return feedDescription.get();
	}

	public void setFeedDescription(String feedDescription) {
		this.feedDescription.set(feedDescription);
	}

	public StringProperty feedDescriptionProperty() {
		return feedDescription;
	}
}
