/*
Copyright 2014 Thomas Scheuchzer, java-adventures.com

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
package com.ja.rsc.extendedecho;

import java.io.Closeable;

import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.GenericManagedConnectionFactory;
import com.ja.rsc.UrlBasedManagedConnection;
import com.ja.rsc.UrlBasedManagedConnectionFactory;
import com.ja.rsc.extendedecho.api.ExtendedEchoConnection;
import com.ja.rsc.extendedecho.api.ExtendedEchoConnectionFactory;

/**
 * This factory creates the connection instances for injection as well as
 * managed instances for container internal life cycle management.
 * 
 * We introduce an additional configuration parameter with name token in this
 * class!
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
@ConnectionDefinition(connectionFactory = ExtendedEchoConnectionFactory.class, connectionFactoryImpl = InMemoryExtendedEchoConnectionFactory.class, connection = ExtendedEchoConnection.class, connectionImpl = InMemoryExtendedEchoConnection.class)
public class ExtendedEchoManagedConnectionFactory extends
		UrlBasedManagedConnectionFactory<ExtendedUrlConnectionConfiguration> {

	private static final long serialVersionUID = 1L;

	public ExtendedEchoManagedConnectionFactory() {
		super(new ExtendedUrlConnectionConfiguration());
	}

	@ConfigProperty(supportsDynamicUpdates = true, description = "Some kind of token")
	public void setToken(String token) {
		this.connectionConfiguration.setToken(token);
	}

	@Override
	protected Object createConnectionFactory(
			GenericManagedConnectionFactory mcf, ConnectionManager cm) {
		return new InMemoryExtendedEchoConnectionFactory(mcf, cm);
	}

	@Override
	protected ManagedConnection createManagedConnection(
			ExtendedUrlConnectionConfiguration connectionConfig,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {

		return new UrlBasedManagedConnection<ExtendedUrlConnectionConfiguration, InMemoryExtendedEchoConnection>(
				connectionConfig, mcf, connectionRequestInfo) {

			@Override
			protected InMemoryExtendedEchoConnection createConnection(
					ExtendedUrlConnectionConfiguration connectionConfiguration,
					ManagedConnectionFactory mcf,
					ConnectionRequestInfo connectionRequestInfo,
					Closeable managedConnection) {
				return new InMemoryExtendedEchoConnection(
						connectionConfiguration, managedConnection);
			}
		};

	}

}
