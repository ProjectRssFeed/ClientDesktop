package cd.rssfeed;

import java.util.concurrent.Future;
import java.util.function.Function;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import cd.rssfeed.model.FeedSource;

public class APIRequest {

	public HttpResponse<JsonNode> postFeedSource(String url, Function<HttpResponse<JsonNode>, Void> onSuccess, FeedSource newSource) {
		String reqUrl = url + "/rss/";
		HttpResponse<JsonNode> jsonResponse = null;

		JSONObject postObj = new JSONObject();
		postObj.put("Link", newSource.getFeedSourceURL());
		try {
			jsonResponse = Unirest.post(reqUrl)
			        .header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        .body(postObj.toString())
			        .asJson();
			jsonResponse.getStatus();
			onSuccess.apply(jsonResponse);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}

	public Future<HttpResponse<JsonNode>> getFeedSourceList(String url, Function<HttpResponse<JsonNode>, Void> onSuccess) {
		String reqUrl = url + "/rss/";
		Future<HttpResponse<JsonNode>> jsonResponse = null;
		jsonResponse = Unirest.get(reqUrl)
		        .header("accept", "application/json")
		        .header("Content-Type", "application/json")
		        .asJsonAsync(new Callback<JsonNode>() {
		            @Override
		            public void completed(HttpResponse<JsonNode> response) {
		            	onSuccess.apply(response);
		            }

		            @Override
		            public void failed(UnirestException e) {
		                System.out.println("failed");
		            }

		            @Override
		            public void cancelled() {
		                System.out.println("cancelled");
		            }
		        });

		return jsonResponse;
	}

	public Future<HttpResponse<JsonNode>> getFeedsFromFeedSource(String url, Function<HttpResponse<JsonNode>, Void> onSuccess, FeedSource feedSource) {
		String reqUrl = url + "/rss/" + feedSource.getFeedSourceId();
		Future<HttpResponse<JsonNode>> jsonResponse = null;
		jsonResponse = Unirest.get(reqUrl)
		        .header("accept", "application/json")
		        .header("Content-Type", "application/json")
		        .asJsonAsync(new Callback<JsonNode>() {
		            @Override
		            public void completed(HttpResponse<JsonNode> response) {
		            	onSuccess.apply(response);
		            }

		            @Override
		            public void failed(UnirestException e) {
		                System.out.println("failed");
		            }

		            @Override
		            public void cancelled() {
		                System.out.println("cancelled");
		            }
		        });

		return jsonResponse;
	}

	public HttpResponse<JsonNode> deleteFeedSource(String url, Function<HttpResponse<JsonNode>, Void> onSuccess, FeedSource removedSource) {
		String reqUrl = url + "/rss/" + removedSource.getFeedSourceId();
		HttpResponse<JsonNode> jsonResponse = null;

		try {
			jsonResponse = Unirest.delete(reqUrl)
			        .header("accept", "application/json")
			        .header("Content-Type", "application/json")
			        .asJson();
			jsonResponse.getStatus();
			onSuccess.apply(jsonResponse);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResponse;
	}
}
