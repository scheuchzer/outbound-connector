package com.ja.rsc.dummyra;

import java.io.Closeable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ja.rsc.ConfigurableConnection;

public class TimeDummyConnection extends ConfigurableConnection<TimeConfig>
		implements DummyConnection {
	private Logger log = LoggerFactory.getLogger(TimeDummyConnection.class);

	public TimeDummyConnection(TimeConfig connectionConfiguration,
			Closeable closeable) {
		super(connectionConfiguration, closeable);
	}

	@Override
	public String getTime() {
		String format = getConnectionConfiguration().getFormat();
		if (format != null) {
			log.info("getTime called. Format is: {}", format);
			return new SimpleDateFormat(format).format(new Date());
		}
		return new Date().toString();
	}

}
