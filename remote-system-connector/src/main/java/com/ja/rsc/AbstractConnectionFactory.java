/*
Copyright 2013 Thomas Scheuchzer, java-adventures.com
 Inspired by class org.connectorz.files.store.FileBucketStore by Adam Bien.
 - Abstract class
 - Generic type added
 
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

import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ja.rsc.api.ConnectionFactory;

/**
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_TYPE>
 *            The class type of the connection interface
 */
abstract public class AbstractConnectionFactory<CONNECTION_TYPE> implements
		ConnectionFactory<CONNECTION_TYPE> {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory
			.getLogger(AbstractConnectionFactory.class);
	private ManagedConnectionFactory mcf;
	private Reference reference;
	private ConnectionManager cm;

	public AbstractConnectionFactory(ManagedConnectionFactory mcf,
			ConnectionManager cm) {
		this.mcf = mcf;
		this.cm = cm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CONNECTION_TYPE getConnection() {
		log.debug("getConnection {} MCF: {}", this.cm, this.mcf);
		try {
			return (CONNECTION_TYPE) cm.allocateConnection(mcf,
					getConnectionRequestdebug());
		} catch (ResourceException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public void setReference(Reference reference) {
		this.reference = reference;
	}

	@Override
	public Reference getReference() {
		return reference;
	}

	protected ConnectionRequestInfo getConnectionRequestdebug() {
		return new ConnectionRequestInfo() {

			@Override
			public boolean equals(Object obj) {
				return true;
			}

			@Override
			public int hashCode() {
				return 1;
			}
		};
	}
}
