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

import static org.mockito.Mockito.verify;

import java.io.Closeable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractConnectionTest {

	@Mock
	private Closeable closeable;

	@Test
	public void autoClose() throws Exception {

		try (DummyConnection con = new DummyConnection(closeable)) {
			con.begin();
		}
		verify(this.closeable).close();
	}

	static class DummyConnection extends
			UrlBasedConnection<UrlConnectionConfiguration> {

		public DummyConnection(Closeable closeable) {
			super(new UrlConnectionConfiguration(), closeable);
		}

	}
}
