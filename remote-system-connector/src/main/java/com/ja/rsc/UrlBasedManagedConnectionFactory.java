/*
Copyright 2013 Thomas Scheuchzer, java-adventures.com
Based on class org.connectorz.files.store.GenericManagedConnectionFactory by Adam Bien
- Properties url, username and password added.
- @ConnectionDefinition removed
- Abstract class

Copyright 2012 Adam Bien, adam-bien.com

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
package com.ja.rsc;

import java.util.Objects;

import javax.resource.spi.ConfigProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Connection factory that defines url, username and password config properties
 * that have to be provided when you setup your connection pool.
 * </p>
 * <p>
 * If your resource adapter requires additional properties, add them and
 * annotate the setter method with {@code @ConfigProperty}. For storing your
 * properties extend the class {@link UrlConnectionConfiguration} and override
 * {@link UrlConnectionConfiguration#hashCode()} and
 * {@link UrlConnectionConfiguration#equals(Object)}
 * </p>
 * <p>
 * If you extend this factory class, this is also the class that has to provides
 * the container configuration. Add the {@code @ConnectionDefinitoin} annotation
 * to your implementation!
 * 
 * <pre>
 * @ConnectionDefinition(connectionFactory = YourConnectionFactoryInterface.class,
 * connectionFactoryImpl = YourConnectionFactoryImplementation.class,
 *  connection = YourConnectionInterface.class,
 *  connectionImpl = YourConnectionImplementation.class)
 * </pre>
 * 
 * </p>
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
abstract public class UrlBasedManagedConnectionFactory<CONNECTION_CONFIGURATION_TYPE extends UrlConnectionConfiguration> extends
		ConfigurableManagedConnectionFactory<CONNECTION_CONFIGURATION_TYPE> {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory
			.getLogger(UrlBasedManagedConnectionFactory.class);
	protected CONNECTION_CONFIGURATION_TYPE connectionConfiguration;

	public UrlBasedManagedConnectionFactory(CONNECTION_CONFIGURATION_TYPE connectionConfiguration) {
		super();
		log.debug("{}#constructor", getClass().getName());
		this.connectionConfiguration = connectionConfiguration;
	}

	@ConfigProperty(supportsDynamicUpdates = true, description = "The url of the remote system")
	public void setUrl(String url) {
		this.connectionConfiguration.setUrl(url);
	}

	@ConfigProperty(supportsDynamicUpdates = true, description = "The login username for the remote system")
	public void setUsername(String username) {
		this.connectionConfiguration.setUsername(username);
	}

	@ConfigProperty(confidential = true, supportsDynamicUpdates = true, description = "The login password for the remote system")
	public void setPassword(String password) {
		this.connectionConfiguration.setPassword(password);
	}

	@Override
	protected CONNECTION_CONFIGURATION_TYPE getConnectionConfiguration() {
		return connectionConfiguration;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UrlBasedManagedConnectionFactory<?> other = (UrlBasedManagedConnectionFactory<?>) obj;
		return connectionConfiguration.equals(other
				.getConnectionConfiguration());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(getClass());
		hash = 71 * hash + Objects.hashCode(connectionConfiguration);
		return hash;
	}

}
