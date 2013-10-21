package com.ja.rsc;

import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.api.Connection;

/**
 * Base implementation of a {@link ManagedConnection} that can be used for
 * handling {@link UrlBasedConnection}s.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_TYPE>
 *            A {@link Connection} that extends {@link UrlBasedConnection}.
 */
public abstract class UrlBasedManagedConnection<CONNECTION_CONFIGURATION_TYPE extends UrlConnectionConfiguration, CONNECTION_TYPE extends UrlBasedConnection<CONNECTION_CONFIGURATION_TYPE>>
		extends
		ConfigurableManagedConnection<CONNECTION_TYPE, CONNECTION_CONFIGURATION_TYPE> {

	public UrlBasedManagedConnection(
			CONNECTION_CONFIGURATION_TYPE connectionConfiguration,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {
		super(connectionConfiguration, mcf, connectionRequestInfo);
	}

}
