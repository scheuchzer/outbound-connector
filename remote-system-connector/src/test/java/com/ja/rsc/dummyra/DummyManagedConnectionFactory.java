package com.ja.rsc.dummyra;

import java.io.Closeable;

import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.ConfigurableManagedConnection;
import com.ja.rsc.ConfigurableManagedConnectionFactory;
import com.ja.rsc.GenericManagedConnectionFactory;

@ConnectionDefinition(connectionFactory = DummyConnectionFactory.class, connectionFactoryImpl = TimeDummyConnectionFactory.class, connection = DummyConnection.class, connectionImpl = TimeDummyConnection.class)
public class DummyManagedConnectionFactory extends
		ConfigurableManagedConnectionFactory<TimeConfig> {

	private static final long serialVersionUID = 1L;

	private TimeConfig timeConfig = new TimeConfig();

	@Override
	protected TimeConfig getConnectionConfiguration() {
		return timeConfig;
	}

	@ConfigProperty(supportsDynamicUpdates = true, description = "Date format")
	public void setFormat(String format) {
		this.timeConfig.setFormat(format);
	}

	@Override
	protected ManagedConnection createManagedConnection(
			TimeConfig connectionConfig, ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {
		return new ConfigurableManagedConnection<TimeDummyConnection, TimeConfig>(
				timeConfig, mcf, connectionRequestInfo) {

			@Override
			protected TimeDummyConnection createConnection(
					TimeConfig connectionConfiguration,
					ManagedConnectionFactory mcf,
					ConnectionRequestInfo connectionRequestInfo,
					Closeable managedConnection) {
				return new TimeDummyConnection(connectionConfiguration,
						managedConnection);
			}
		};

	}

	@Override
	protected Object createConnectionFactory(
			GenericManagedConnectionFactory mcf, ConnectionManager cxManager) {
		return new TimeDummyConnectionFactory(mcf, cxManager);
	}

}
