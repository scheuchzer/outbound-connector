package com.ja.rsc;

import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

/**
 * 
 * Base implementation of a {@link ManagedConnection} that can be used for
 * handling {@link ConfigurableConnection}s.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_TYPE>
 * @param <CONNECTION_CONFIGURATION_TYPE>
 */
public abstract class ConfigurableManagedConnection<CONNECTION_TYPE extends ConfigurableConnection<CONNECTION_CONFIGURATION_TYPE>, CONNECTION_CONFIGURATION_TYPE>
		extends
		GenericManagedConnection<CONNECTION_TYPE, CONNECTION_CONFIGURATION_TYPE> {

	public ConfigurableManagedConnection(
			CONNECTION_CONFIGURATION_TYPE connectionConfiguration,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {
		super(connectionConfiguration, mcf, connectionRequestInfo);
	}
}
