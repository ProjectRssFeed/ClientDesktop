package cd.rssfeed;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import cd.rssfeed.model.FeedSource;

public class APIRequest {

	public String postFeedSource(String url) {
		String reqUrl = url + "/rss/";
		Request request = new Request(reqUrl, Request.RequestType.POST);

		return request.getJSON();
	}

	public HttpResponse<JsonNode> getFeedSourceList(String url) {
		String reqUrl = url + "/rss/";
		HttpResponse<JsonNode> jsonResponse = null;
		try {
			jsonResponse = Unirest.get(reqUrl)
			        .header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        .asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

	public HttpResponse<JsonNode> getFeedsFromFeedSource(String url, FeedSource feedSource) {
		String reqUrl = url + "/rss/" + feedSource.getFeedSourceId();
		HttpResponse<JsonNode> jsonResponse = null;
		try {
			jsonResponse = Unirest.get(reqUrl)
			        .header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        .asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return jsonResponse;
	}

	public String deleteFeedSource(String url, FeedSource feedSource) {
		String reqUrl = url + "/rss/" + feedSource.getFeedSourceId();
		Request request = new Request(reqUrl, Request.RequestType.DELETE);

		return request.getJSON();
	}
}
