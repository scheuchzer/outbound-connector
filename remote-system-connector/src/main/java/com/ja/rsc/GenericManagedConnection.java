/*
 Copyright 2013 Thomas Scheuchzer, java-adventures.com
 Based on class org.connectorz.files.store.GenericManagedConnection by Adam Bien.
 - Properties url, username, password added
 - abstract class
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

import static javax.resource.spi.ConnectionEvent.CONNECTION_CLOSED;
import static javax.resource.spi.ConnectionEvent.LOCAL_TRANSACTION_COMMITTED;
import static javax.resource.spi.ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK;
import static javax.resource.spi.ConnectionEvent.LOCAL_TRANSACTION_STARTED;

import java.io.Closeable;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base implementation of a {@link ManagedConnection} that can be used for
 * handling {@link AbstractConnection}s.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_TYPE>
 * @param <CONNECTION_CONFIGURATION_TPYE>
 */
abstract public class GenericManagedConnection<CONNECTION_TYPE extends AbstractConnection, CONNECTION_CONFIGURATION_TPYE>
		implements ManagedConnection, LocalTransaction, Closeable {
	private Logger log = LoggerFactory
			.getLogger(GenericManagedConnection.class);
	private CONNECTION_TYPE remoteConnection;
	private ConnectionRequestInfo connectionRequestInfo;
	private List<ConnectionEventListener> listeners;
	private PrintWriter out;
	private ManagedConnectionFactory mcf;

	public GenericManagedConnection(
			CONNECTION_CONFIGURATION_TPYE connectionConfiguration,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo) {
		this.mcf = mcf;
		this.connectionRequestInfo = connectionRequestInfo;
		this.listeners = new LinkedList<>();
		this.remoteConnection = createConnection(connectionConfiguration, mcf,
				connectionRequestInfo, this);
	}

	protected abstract CONNECTION_TYPE createConnection(
			CONNECTION_CONFIGURATION_TPYE connectionConfiguration,
			ManagedConnectionFactory mcf,
			ConnectionRequestInfo connectionRequestInfo,
			Closeable managedConnection);

	protected ManagedConnectionFactory getManagedConnectionFactory() {
		return mcf;
	}

	@Override
	public Object getConnection(Subject subject,
			ConnectionRequestInfo connectionRequestInfo)
			throws ResourceException {
		log.debug("{}#getConnection", getClass().getName());
		return remoteConnection;
	}

	@Override
	public void destroy() {
		log.debug("{}#destroy", getClass().getName());
		this.remoteConnection.destroy();
	}

	@Override
	public void cleanup() {
		log.debug("{}#cleanup", getClass().getName());
		this.remoteConnection.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void associateConnection(Object connection) {
		log.debug("{}#assosiateConnection", getClass().getName());
		this.remoteConnection = (CONNECTION_TYPE) connection;

	}

	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {
		log.debug("{}#addConnectionEventListener", getClass().getName());
		this.listeners.add(listener);
	}

	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		log.debug("{}#removeConnectionEventListener", getClass().getName());
		this.listeners.remove(listener);
	}

	@Override
	public XAResource getXAResource() throws ResourceException {
		log.debug("{}#getXAResource", getClass().getName());
		throw new ResourceException(
				"XA protocol is not supported by this adapter");
	}

	@Override
	public LocalTransaction getLocalTransaction() {
		log.debug("{}#getLocalTransaction", getClass().getName());
		return this;
	}

	@Override
	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		log.debug("{}#getMetaData", getClass().getName());
		return new ManagedConnectionMetaData() {

			@Override
			public String getEISProductName() throws ResourceException {
				log.debug("{}#getEISProductName", getClass().getName());
				return "Generic JCA";
			}

			@Override
			public String getEISProductVersion() throws ResourceException {
				log.debug("{}#getEISProductVersion", getClass().getName());
				return "1.0";
			}

			@Override
			public int getMaxConnections() throws ResourceException {
				log.debug("{}#getMaxConnections", getClass().getName());
				return 5;
			}

			@Override
			public String getUserName() throws ResourceException {
				return null;
			}
		};
	}

	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		this.out = out;
	}

	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return out;
	}

	ConnectionRequestInfo getConnectionRequestdebug() {
		return connectionRequestInfo;
	}

	@Override
	public void begin() throws ResourceException {
		this.remoteConnection.begin();
		this.fireConnectionEvent(LOCAL_TRANSACTION_STARTED);
	}

	@Override
	public void commit() throws ResourceException {
		this.remoteConnection.commit();
		this.fireConnectionEvent(LOCAL_TRANSACTION_COMMITTED);
	}

	@Override
	public void rollback() throws ResourceException {
		this.remoteConnection.rollback();
		this.fireConnectionEvent(LOCAL_TRANSACTION_ROLLEDBACK);
	}

	public void fireConnectionEvent(int event) {
		ConnectionEvent connnectionEvent = new ConnectionEvent(this, event);
		connnectionEvent.setConnectionHandle(this.remoteConnection);
		for (ConnectionEventListener listener : this.listeners) {
			switch (event) {
			case LOCAL_TRANSACTION_STARTED:
				listener.localTransactionStarted(connnectionEvent);
				break;
			case LOCAL_TRANSACTION_COMMITTED:
				listener.localTransactionCommitted(connnectionEvent);
				break;
			case LOCAL_TRANSACTION_ROLLEDBACK:
				listener.localTransactionRolledback(connnectionEvent);
				break;
			case CONNECTION_CLOSED:
				listener.connectionClosed(connnectionEvent);
				break;
			default:
				throw new IllegalArgumentException("Unknown event: " + event);
			}
		}
	}

	@Override
	public void close() {
		this.fireConnectionEvent(CONNECTION_CLOSED);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GenericManagedConnection other = (GenericManagedConnection) obj;
		if (this.connectionRequestInfo != other.connectionRequestInfo
				&& (this.connectionRequestInfo == null || !this.connectionRequestInfo
						.equals(other.connectionRequestInfo))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83
				* hash
				+ (this.connectionRequestInfo != null ? this.connectionRequestInfo
						.hashCode() : 0);
		return hash;
	}

}
