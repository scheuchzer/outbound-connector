/*
 Copyright 2013 Thomas Scheuchzer, java-adventures.com
 Inspired by class org.connectorz.files.store.FileBucket by Adam Bien.
 - Properties url, username, password added
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

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.resource.ResourceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a basic connection that handles the tx lifecycle methods
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public abstract class AbstractConnection implements AutoCloseable {

	private Logger log = LoggerFactory.getLogger(AbstractConnection.class);

	private ConcurrentHashMap<String, byte[]> txCache;
	private Closeable closeable;

	public AbstractConnection(Closeable closeable) {
		this.closeable = closeable;
		this.txCache = new ConcurrentHashMap<>();
		log.debug("{}: {}", getClass().getName(), toString());
	}

	public void begin() throws ResourceException {
		log.debug("{}#begin", getClass().getName());
	}

	public void commit() throws ResourceException {
		log.debug("{}#commit", getClass().getName());
	}

	public void rollback() throws ResourceException {
		log.debug("{}#rollback", getClass().getName());
	}

	public void destroy() {
		log.debug("{}#destroy", getClass().getName());
		this.clear();
	}

	@Override
	public void close() {
		try {
			if (this.closeable != null) {
				this.closeable.close();
			}
		} catch (IOException ex) {
			throw new IllegalStateException(
					"Cannot close GenericManagedConnection", ex);
		}
	}

	public void clear() {
		this.txCache.clear();
	}

	@Override
	public String toString() {
		return "txCache=" + txCache + ", managedConnection=" + closeable + '}';
	}
}
