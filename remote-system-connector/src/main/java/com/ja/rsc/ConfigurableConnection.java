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
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a basic connection that has some kind of configuration.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 * @param <CONNECTION_CONFIGURATION_TYPE>
 *            The class type of the connection configuration.
 * 
 */
public abstract class ConfigurableConnection<CONNECTION_CONFIGURATION_TYPE>
		extends AbstractConnection {

	private Logger log = LoggerFactory.getLogger(ConfigurableConnection.class);
	private CONNECTION_CONFIGURATION_TYPE connectionConfiguration;

	public ConfigurableConnection(
			CONNECTION_CONFIGURATION_TYPE connectionConfiguration,
			Closeable closeable) {
		super(closeable);
		this.connectionConfiguration = connectionConfiguration;
		log.debug("{}: {}", getClass().getName(), toString());
	}

	protected CONNECTION_CONFIGURATION_TYPE getConnectionConfiguration() {
		return connectionConfiguration;
	}

	@Override
	public String toString() {
		return Objects.toString(connectionConfiguration) + ", "
				+ super.toString();
	}
}
