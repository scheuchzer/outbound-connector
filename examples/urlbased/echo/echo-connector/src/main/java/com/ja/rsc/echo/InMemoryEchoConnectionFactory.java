/*
Copyright 2014 Thomas Scheuchzer, java-adventures.com

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
package com.ja.rsc.echo;

import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.AbstractConnectionFactory;
import com.ja.rsc.echo.api.EchoConnection;
import com.ja.rsc.echo.api.EchoConnectionFactory;

/**
 * Implementation for the {@link EchoConnectionFactory}. As you can see no
 * additional code is needed.
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public class InMemoryEchoConnectionFactory extends
		AbstractConnectionFactory<EchoConnection> implements
		EchoConnectionFactory {

	private static final long serialVersionUID = 1L;

	public InMemoryEchoConnectionFactory(ManagedConnectionFactory mcf,
			ConnectionManager cm) {
		super(mcf, cm);
	}

}
