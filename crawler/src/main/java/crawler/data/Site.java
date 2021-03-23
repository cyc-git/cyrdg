package crawler.data;

public class Site {
	private Integer number;
	private String name;
	private String url;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Site [number=" + number + ", name=" + name + ", url=" + url + "]";
	}
}
