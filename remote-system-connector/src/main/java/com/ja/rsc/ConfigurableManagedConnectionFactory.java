/*
Copyright 2014 Thomas Scheuchzer, java-adventures.com
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

import java.io.PrintWriter;
import java.util.Objects;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Connection factory that defines a generic type of connection config
 * properties that have to be provided when you setup your connection pool.
 * </p>
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_CONFIGURATION_TYPE>
 *            The type of the object that contains the connection configuration
 *            properties. The class MUST override {@link Object#hashCode()} and
 *            {@link Object#equals()}
 * 
 */
abstract public class ConfigurableManagedConnectionFactory<CONNECTION_CONFIGURATION_TYPE>
		extends GenericManagedConnectionFactory {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory
			.getLogger(ConfigurableManagedConnectionFactory.class);
	private PrintWriter out;

	public ConfigurableManagedConnectionFactory() {
		super();
		log.debug("{}#constructor", getClass().getName());
	}

	@Override
	public ManagedConnection createManagedConnection(Subject subject,
			ConnectionRequestInfo connectionRequestInfo) {
		log.debug("{}#createManagedConnection", getClass().getName());
		return createManagedConnection(getConnectionConfiguration(), this,
				connectionRequestInfo);
	}

	abstract protected CONNECTION_CONFIGURATION_TYPE getConnectionConfiguration();

	abstract protected ManagedConnection createManagedConnection(
			CONNECTION_CONFIGURATION_TYPE connectionConfig,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo);

	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		this.out = out;
	}

	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConfigurableManagedConnectionFactory<?> other = (ConfigurableManagedConnectionFactory<?>) obj;
		return Objects.equals(this.getConnectionConfiguration(),
				other.getConnectionConfiguration());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(getClass());
		hash = 71 * hash + Objects.hashCode(getConnectionConfiguration());
		return hash;
	}

}
