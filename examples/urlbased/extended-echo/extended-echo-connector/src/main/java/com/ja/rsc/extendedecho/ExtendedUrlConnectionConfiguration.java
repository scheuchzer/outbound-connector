package com.ja.rsc.extendedecho;

import java.util.Objects;

import com.ja.rsc.UrlConnectionConfiguration;

public class ExtendedUrlConnectionConfiguration extends
		UrlConnectionConfiguration {

	private String token;

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), token);
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			ExtendedUrlConnectionConfiguration other = (ExtendedUrlConnectionConfiguration) obj;
			return Objects.equals(token, other.token);
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + ", token=" + token;
	}
}
