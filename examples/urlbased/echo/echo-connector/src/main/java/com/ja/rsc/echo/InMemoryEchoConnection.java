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
package com.ja.rsc.echo;

import java.io.Closeable;

import com.ja.rsc.UrlBasedConnection;
import com.ja.rsc.UrlConnectionConfiguration;
import com.ja.rsc.echo.api.EchoConnection;
import com.ja.rsc.echo.api.EchoResponse;

/**
 * Example implementation that works fully in-memory without any remote
 * invocation. Just for the sake of demonstration the {@link EchoResponse} gets
 * populated with url, username and password configured with the connection.
 * Real connection implementation would use this information to open a
 * connection to a remote system.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public class InMemoryEchoConnection extends
		UrlBasedConnection<UrlConnectionConfiguration> implements
		EchoConnection {

	public InMemoryEchoConnection(
			UrlConnectionConfiguration connectionConfiguration,
			Closeable closeable) {
		super(connectionConfiguration, closeable);
	}

	@Override
	public EchoResponse echo(String text) {
		EchoResponse response = new EchoResponse();
		response.setText(text);
		response.setUrl(getUrl());
		response.setUsername(getUsername());
		response.setPassword(getPassword());
		return response;
	}

}
