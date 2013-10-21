/*
Copyright 2013 Thomas Scheuchzer, java-adventures.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.ja.rsc.extendedecho.api;

/**
 * A sample response. Just for demonstration the resource adapter properties
 * url, username and password are included in this response.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public class ExtendedEchoResponse {

	private String text;
	private String url;
	private String username;
	private String password;
	private String token;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return String.format("text=%s, url=%s, username=%s, password=%s, token=%s", text,
				url, username, password, token);
	}
}
