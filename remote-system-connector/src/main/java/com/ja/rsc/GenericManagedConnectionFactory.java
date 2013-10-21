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

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Connection factory that defines url, username and password config properties
 * that have to be provided when you setup your connection pool.
 * </p>
 * <p>
 * If your resource adapter requires additional properties, add them and
 * annotate the setter method with @ConfigProperty. Also extend the methods
 * {@link #hashCode()} and {@link #equals(Object)}.
 * </p>
 * This is also the class that provides the container configuration. Add the
 * 
 * @ConnectionDefinitoin annotation to your implementation!
 * 
 *                       <pre>
 * @ConnectionDefinition(connectionFactory = YourConnectionFactoryInterface.class,
 * connectionFactoryImpl = YourConnectionFactoryImplementation.class,
 *  connection = YourConnectionInterface.class,
 *  connectionImpl = YourConnectionImplementation.class)
 * </pre>
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
abstract public class GenericManagedConnectionFactory implements
		ManagedConnectionFactory, Serializable {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory
			.getLogger(GenericManagedConnectionFactory.class);
	private PrintWriter out;

	public GenericManagedConnectionFactory() {
		log.debug("{}#constructor", getClass().getName());
	}

	@Override
	public Object createConnectionFactory(ConnectionManager cm)
			throws ResourceException {
		log.debug("{}#createConnectionFactory,1", getClass().getName());
		return createConnectionFactory(this, cm);
	}

	protected abstract Object createConnectionFactory(
			GenericManagedConnectionFactory mcf, ConnectionManager cxManager);

	@Override
	public Object createConnectionFactory() throws ResourceException {
		log.debug("{}#createConnectionFactory,2", getClass().getName());
		return createConnectionFactory(this, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ManagedConnection matchManagedConnections(Set connectionSet,
			Subject subject, ConnectionRequestInfo info)
			throws ResourceException {
		log.debug("{}#matchManagedConnections Subject={}, Info={}", getClass()
				.getName(), subject, info);
		for (Object con : connectionSet) {
			GenericManagedConnection gmc = (GenericManagedConnection) con;
			ConnectionRequestInfo connectionRequestInfo = gmc
					.getConnectionRequestdebug();
			if ((info == null) || connectionRequestInfo.equals(info))
				return gmc;
		}
		throw new ResourceException("Cannot find connection for info!");
	}

	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		this.out = out;
	}

	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return this.out;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return getClass().equals(obj.getClass());
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(getClass());
		return hash;
	}

}
