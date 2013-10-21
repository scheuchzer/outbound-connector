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

import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.GenericManagedConnectionFactory;
import com.ja.rsc.UrlBasedManagedConnection;
import com.ja.rsc.UrlBasedManagedConnectionFactory;
import com.ja.rsc.UrlConnectionConfiguration;
import com.ja.rsc.echo.api.EchoConnection;
import com.ja.rsc.echo.api.EchoConnectionFactory;

/**
 * This factory creates the connection instances for injection as well as
 * managed instances for container internal life cycle management.
 * 
 * If we would have resource adapter specific config properties except url,
 * username and password we would have to add them here.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
@ConnectionDefinition(connectionFactory = EchoConnectionFactory.class, connectionFactoryImpl = InMemoryEchoConnectionFactory.class, connection = EchoConnection.class, connectionImpl = InMemoryEchoConnection.class)
public class EchoManagedConnectionFactory extends
		UrlBasedManagedConnectionFactory<UrlConnectionConfiguration> {

	private static final long serialVersionUID = 1L;

	public EchoManagedConnectionFactory() {
		super(new UrlConnectionConfiguration());
	}

	@Override
	protected Object createConnectionFactory(
			GenericManagedConnectionFactory mcf, ConnectionManager cm) {
		return new InMemoryEchoConnectionFactory(mcf, cm);
	}

	@Override
	protected ManagedConnection createManagedConnection(
			UrlConnectionConfiguration connectionConfig,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {

		return new UrlBasedManagedConnection<UrlConnectionConfiguration, InMemoryEchoConnection>(
				connectionConfig, mcf, connectionRequestInfo) {

			@Override
			protected InMemoryEchoConnection createConnection(
					UrlConnectionConfiguration connectionConfiguration,
					ManagedConnectionFactory mcf,
					ConnectionRequestInfo connectionRequestInfo,
					Closeable managedConnection) {
				return new InMemoryEchoConnection(connectionConfiguration,
						managedConnection);
			}
		};

	}

}
