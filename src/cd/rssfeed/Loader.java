package cd.rssfeed;

import java.util.ArrayList;
import java.util.function.Function;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

import cd.rssfeed.model.Feed;
import cd.rssfeed.model.FeedSource;

/**
 * Loader Class
 * Call API Server and Parse JSON Response, generate Feed / FeedSource Object
 * @author Eric
 *
 */
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

	/**
	 * Parse JSON Response to get FeedSources (Callback)
	 * @return null
	 */
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

	/**
	 * Get FeedSource from Server and parse it
	 */
	public void loadFeedSources() {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedSourceList(url, this.parseFeedSources());
	}

	/**
	 * Parse JSON Response to get FeedSources and associated Feeds
	 * @param loadFeedSourceView
	 * @param loadFeedView
	 * @return
	 */
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

			// Create FeedSource
			FeedSource newFeedSource = new FeedSource(curId, curTitle, curUrl, curDescription);
			// Load Feeds into FeedSource
			loadFeedsFromFeedSource(newFeedSource, loadFeedView);
			feedSources.add(newFeedSource);
		}
		loadFeedSourceView.apply(feedSources);
		return null;
		};
	}

	/**
	 * Get FeedSources and associated Feeds from the server
	 * @param loadFeedSourceView FeedSourceView reload Callback
	 * @param loadFeedView FeedView reload Callback
	 */
	public void loadFeedSourcesAndFeeds(Function<ArrayList<FeedSource>, Void> loadFeedSourceView, Function<FeedSource, Void> loadFeedView) {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedSourceList(url, this.parseFeedSourcesAndFeeds(loadFeedSourceView, loadFeedView));
	}

	/**
	 * Parse JSON Response to get Feeds of a FeedSource
	 * @param feedSource Current FeedSource
	 * @param loadFeedView FeedView reload Callback
	 * @return Callback
	 */
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

	/**
	 * Get Feeds of a FeedSource from Server
	 * @param feedSource Current FeedSource
	 * @param loadFeedView FeedView reload Callback
	 */
	public void loadFeedsFromFeedSource(FeedSource feedSource, Function<FeedSource, Void> loadFeedView) {
		String url = config.getServerURL() + config.getApiVersion();
		api.getFeedsFromFeedSource(url, this.parseFeedFromFeedSource(feedSource, loadFeedView), feedSource);
	}

	/**
	 * Reload Data Callback
	 * @param reloadData
	 * @return
	 */
	private Function<HttpResponse<JsonNode>, Void> onSuccessReloadData(Function<Void, Void> reloadData) {
		return (json) -> {
			reloadData.apply(null);
			return null;
		};
	}

	/**
	 * Post a FeedSource to the server and reload View
	 * @param newSource New FeedSource
	 * @param reloadData View reload Callback
	 */
	public void addNewFeedSource(FeedSource newSource, Function<Void, Void> reloadData) {
		String url = config.getServerURL() + config.getApiVersion();
		api.postFeedSource(url, onSuccessReloadData(reloadData), newSource);
	}

	/**
	 * Remove a FeedSource from the server and reload View
	 * @param removedSource FeedSource to be removed
	 * @param reloadData View reload Callback
	 */
	public void removeFeedSource(FeedSource removedSource, Function<Void, Void> reloadData) {
		String url = config.getServerURL() + config.getApiVersion();
		api.deleteFeedSource(url, onSuccessReloadData(reloadData), removedSource);
	}
}
