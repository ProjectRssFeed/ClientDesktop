package cd.rssfeed;

import java.util.ArrayList;
import java.util.function.Function;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import cd.rssfeed.model.Feed;
import cd.rssfeed.model.FeedSource;

public class Loader {
	private APIRequest api = null;
	private Configuration config = null;

	public Loader(Configuration config) {
		this.config = config;
		api = new APIRequest();
	}

	public void setConfiguration(Configuration config) {
		this.config = config;
	}

	private Function<HttpResponse<JsonNode>, Void> parseFeedSources() {
		return (json) -> {
		JSONArray dataArray = json.getBody().getArray();
		ArrayList<FeedSource> feedSources = new ArrayList<FeedSource>();

		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject cur = dataArray.getJSONObject(i);

			Integer curId = 0;
			String curTitle = "none";
			String curUrl = "none";
			String curDescription = "none";

			try {
				curId = cur.getInt("Id");
				curTitle = cur.getString("Title");
				curUrl = "none";
				curDescription = cur.getString("Description");
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			feedSources.add(new FeedSource(curId, curTitle, curUrl, curDescription));
		}
		return null;
		};
	}

	public void loadFeedSources() {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedSourceList(url, this.parseFeedSources());
	}

	private Function<HttpResponse<JsonNode>, Void> parseFeedSourcesAndFeeds(Function<ArrayList<FeedSource>, Void> loadFeedSourceView, Function<FeedSource, Void> loadFeedView) {
		return (json) -> {
		JSONArray dataArray = json.getBody().getArray();

		ArrayList<FeedSource> feedSources = new ArrayList<FeedSource>();

		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject cur = dataArray.getJSONObject(i);

			Integer curId = 0;
			String curTitle = "none";
			String curUrl = "none";
			String curDescription = "none";

			try {
				curId = cur.getInt("Id");
				curTitle = cur.getString("Title");
				curUrl = "none";
				curDescription = cur.getString("Description");
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			FeedSource newFeedSource = new FeedSource(curId, curTitle, curUrl, curDescription);
			loadFeedsFromFeedSource(newFeedSource, loadFeedView);
			feedSources.add(newFeedSource);
		}
		loadFeedSourceView.apply(feedSources);
		return null;
		};
	}

	public void loadFeedSourcesAndFeeds(Function<ArrayList<FeedSource>, Void> loadFeedSourceView, Function<FeedSource, Void> loadFeedView) {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedSourceList(url, this.parseFeedSourcesAndFeeds(loadFeedSourceView, loadFeedView));
	}

	private Function<HttpResponse<JsonNode>, Void> parseFeedFromFeedSource(FeedSource feedSource, Function<FeedSource, Void> loadFeedView) {
		return (json) -> {
		JSONArray dataArray = json.getBody().getArray();
		ArrayList<Feed> feeds = new ArrayList<Feed>();

		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject cur = dataArray.getJSONObject(i);

			String curTitle = "none";
			String curUrl = "none";
			String curDescription = "none";

			try {
				curTitle = cur.getString("Title");
				curUrl = cur.getString("Link");
				curDescription = cur.getString("Description");
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			feeds.add(new Feed(curTitle, curUrl, curDescription));
		}
		feedSource.setFeedList(feeds);
		loadFeedView.apply(feedSource);
		return null;
		};
	}

	public void loadFeedsFromFeedSource(FeedSource feedSource, Function<FeedSource, Void> loadFeedView) {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedsFromFeedSource(url, this.parseFeedFromFeedSource(feedSource, loadFeedView), feedSource);
	}

	private Function<HttpResponse<JsonNode>, Void> onSuccessReloadData(Function<Void, Void> reloadData) {
		return (json) -> {
			reloadData.apply(null);
			return null;
		};
	}

	public void addNewFeedSource(FeedSource newSource, Function<Void, Void> reloadData) {
		String url = config.getServerURL() + config.getApiVersion();
		api.postFeedSource(url, onSuccessReloadData(reloadData), newSource);
	}

	public void removeFeedSource(FeedSource removedSource, Function<Void, Void> reloadData) {
		String url = config.getServerURL() + config.getApiVersion();
		api.deleteFeedSource(url, onSuccessReloadData(reloadData), removedSource);
	}
}
