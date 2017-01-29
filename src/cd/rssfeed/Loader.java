package cd.rssfeed;

import java.util.ArrayList;

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

	public ArrayList<FeedSource> loadFeedSources() {
		ArrayList<FeedSource> feedSources = new ArrayList<FeedSource>();
		String url = config.getServerURL() + config.getApiVersion();
		HttpResponse<JsonNode> json = api.getFeedSourceList(url);

		JSONArray dataArray = json.getBody().getArray();

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
		return feedSources;
	}

	public ArrayList<FeedSource> loadFeedSourcesAndFeeds() {
		ArrayList<FeedSource> feedSources = new ArrayList<FeedSource>();
		String url = config.getServerURL() + config.getApiVersion();
		HttpResponse<JsonNode> json = api.getFeedSourceList(url);

		if (json == null)
			return feedSources;
		JSONArray dataArray = json.getBody().getArray();

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
			newFeedSource = loadFeedsFromFeedSource(newFeedSource);
			feedSources.add(newFeedSource);
		}
		return feedSources;
	}

	public FeedSource loadFeedsFromFeedSource(FeedSource feedSource) {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		String url = config.getServerURL() + config.getApiVersion();
		HttpResponse<JsonNode> json = api.getFeedsFromFeedSource(url, feedSource);

		if (json == null)
			return feedSource;

		JSONArray dataArray = json.getBody().getArray();

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
		return feedSource;
	}
}
