package hk.hku.cs.msc.ules.dto;

public class RequestData {
	
	private String username;
	private String password;
	
	private String url;
	private String randomKey;
	private String mountKey;

	public RequestData(){
		super();
	}

	public RequestData(String url, String randomKey) {
		super();
		this.url = url;
		this.randomKey = randomKey;
	}
	

	public RequestData(String username, String url, String randomKey) {
		super();
		this.username = username;
		this.url = url;
		this.randomKey = randomKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRandomKey() {
		return randomKey;
	}

	public void setRandomKey(String randomKey) {
		this.randomKey = randomKey;
	}

	public String getMountKey() {
		return mountKey;
	}

	public void setMountKey(String mountKey) {
		this.mountKey = mountKey;
	}
	
	
}
