/*
Copyright 2013 Thomas Scheuchzer, java-adventures.com

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

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

/**
 * <p>
 * Implements some bootstrapping methods of a resource adapter to reduce the
 * code of your implementation. If your resource adapter simple calls a remote
 * system by REST or SAOP simply extending this class might be all you have to
 * do. But don't forget to annotate your class with @Connector:
 * </p>
 * 
 * <pre>
 * @Connector( reauthenticationSupport = false, transactionSupport =
 *             TransactionSupport.TransactionSupportLevel.LocalTransaction)
 * </pre>
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 */
abstract public class AbstractAdapter implements ResourceAdapter {

	private BootstrapContext bc;

	@Override
	public void start(BootstrapContext bc) {
		this.bc = bc;
	}

	public BootstrapContext getBootstrapContext() {
		return bc;
	}

	@Override
	public void stop() {
	}

	@Override
	public void endpointActivation(MessageEndpointFactory mef, ActivationSpec as) {
	}

	@Override
	public void endpointDeactivation(MessageEndpointFactory mef,
			ActivationSpec as) {
	}

	@Override
	public XAResource[] getXAResources(ActivationSpec[] ass) {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass().getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return Objects.equals(getClass().getName(), obj.getClass().getName());
	}
}
