package com.ja.rsc.dummyra;

import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

import com.ja.rsc.AbstractConnectionFactory;

public class TimeDummyConnectionFactory extends
		AbstractConnectionFactory<DummyConnection> implements
		DummyConnectionFactory {

	private static final long serialVersionUID = 1L;

	public TimeDummyConnectionFactory(ManagedConnectionFactory mcf,
			ConnectionManager cm) {
		super(mcf, cm);
	}

}
