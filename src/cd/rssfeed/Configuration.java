package cd.rssfeed;

public class Configuration {
	private String serverUrl = "http://163.5.84.111:8443/";
	private String apiVersion = "v0.1";

	public Configuration () {

	}

	public Configuration (String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public Configuration (String serverUrl, String apiVersion) {
		this.serverUrl = serverUrl;
		this.apiVersion = apiVersion;
	}

	public void setServerURL (String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getServerURL () {
		return serverUrl;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}
}
