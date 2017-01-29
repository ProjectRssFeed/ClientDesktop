package cd.rssfeed.model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FeedSource {
	private Integer Id;

	private final StringProperty feedSourceName;
	private final StringProperty feedSourceURL;
	private final StringProperty feedSourceDescription;

	private ArrayList<Feed> feedList;

	public FeedSource() {
		this(null, null, null, null);
	}

	public FeedSource(Integer id, String feedSourceName, String feedSourceURL, String feedSourceDescription) {
		this.Id = id;
		this.feedSourceName = new SimpleStringProperty(feedSourceName);
		this.feedSourceURL = new SimpleStringProperty(feedSourceURL);
		this.feedSourceDescription = new SimpleStringProperty(feedSourceDescription);
		this.feedList = null;
	}

	public Integer getFeedSourceId() {
		return Id;
	}

	public void setFeedSourceId(Integer id) {
		this.Id = id;
	}

	public String getFeedSourceName() {
		return feedSourceName.get();
	}

	public void setFeedSourceName(String feedSourceName) {
		this.feedSourceName.set(feedSourceName);
	}

	public StringProperty feedSourceNameProperty() {
		return feedSourceName;
	}

	public String getFeedSourceURL() {
		return feedSourceURL.get();
	}

	public void setFeedSourceURL(String feedSourceURL) {
		this.feedSourceURL.set(feedSourceURL);
	}

	public StringProperty FeedSourceURLProperty() {
		return feedSourceURL;
	}

	public String getFeedSourceDescription() {
		return feedSourceDescription.get();
	}

	public void setFeedSourceDescription(String feedSourceDescription) {
		this.feedSourceDescription.set(feedSourceDescription);
	}

	public StringProperty feedSourceDescriptionProperty() {
		return feedSourceDescription;
	}

	public ArrayList<Feed> getFeedList() {
		return feedList;
	}

	public void setFeedList(ArrayList<Feed> feedList) {
		this.feedList = feedList;
	}
}
