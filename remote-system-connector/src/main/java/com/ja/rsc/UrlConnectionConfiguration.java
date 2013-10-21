package com.ja.rsc;

import java.util.Objects;

/**
 * Connection configuration that knows url, username and password. If you extend
 * this class don't forget to implement {@link #hashCode()} and
 * {@link #equals(Object)}!
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public class UrlConnectionConfiguration {

	private String url;
	private String username;
	private String password;

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

	@Override
	public int hashCode() {
		return Objects.hash(url, username, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UrlConnectionConfiguration other = (UrlConnectionConfiguration) obj;
		return Objects.equals(url, other.url)
				&& Objects.equals(username, other.username)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "url=" + url + ", username=" + username + ", password="
				+ (password == null ? "n/a" : "******");
	}
}
