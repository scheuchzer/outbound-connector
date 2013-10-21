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
package com.ja.rsc.api;

import java.io.Serializable;
import javax.resource.Referenceable;

/**
 * A {@link ConnectionFactory} provides access to the underlying
 * {@link Connection}. The {@link ConnectionFactory} is the class that you're
 * going to inject into your code.
 * 
 * Your implementation should simply extend this class. No further code should
 * be necessary.
 * 
 * @param <T>
 *            The class type of the connection.
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public interface ConnectionFactory<CONNECTION_TYPE> extends Serializable,
		Referenceable {
	CONNECTION_TYPE getConnection();
}
