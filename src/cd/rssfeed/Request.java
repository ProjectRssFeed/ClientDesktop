package cd.rssfeed;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class Request {

	public enum RequestType {
		POST("POST"),
		GET("GET"),
		DELETE("DELETE");

		private final String type;

		private RequestType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}
	}

	private URL url = null;
	private HttpURLConnection connection;


	public Request(String urlString, RequestType type) {
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(type.toString());
			connection.setRequestProperty("Content-type", "application/json");
			connection.setRequestProperty("charset", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJSON() {
		String json = null;
		try {
			connection.connect();
			InputStream inStream = connection.getInputStream();
			json = IOUtils.toString(inStream, "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
