package com.wptslackintegration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Url {

	private String url;
	
	private String key;
	
	private String location;

	@Override
	public String toString()
	{
		
		return "Url [url=" + url + ", key=" +key+", location=" + location +"]"; 
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
