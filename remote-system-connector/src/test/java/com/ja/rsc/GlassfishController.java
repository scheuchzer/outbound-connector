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
package com.ja.rsc;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishRuntime;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlassfishController extends ExternalResource {
	private Logger log = LoggerFactory.getLogger(GlassfishController.class);
	private static GlassFishRuntime gfr;
	private static GlassFish gf;
	private Deployer deployer;
	private CommandRunner commandRunner;
	private TemporaryFolder tmpFolder = new TemporaryFolder();
	private Stack<CleanupCommand> cleanupStack = new Stack<>();

	@Override
	protected void before() throws Throwable {
		tmpFolder.create();
		start();
	}

	public void start() {
		try {
			if (gfr == null) {
				gfr = GlassFishRuntime.bootstrap();
				gf = gfr.newGlassFish();
				gf.start();
			}
			deployer = gf.getService(Deployer.class, null);
			commandRunner = gf.getCommandRunner();
		} catch (GlassFishException e) {
			throw new RuntimeException("Startup failed", e);
		}
	}

	public void stop() {
		try {
			gf.stop();
			gf.dispose();
			gfr.shutdown();
		} catch (Exception e) {
			throw new RuntimeException("Shutdown failed", e);
		}
		log.info("GF has been shutdown");
	}

	@Override
	protected void after() {
		cleanup();
		//stop();
		tmpFolder.delete();
	}

	public void cleanup() {
		log.info("Executing {} cleanup commands.", cleanupStack.size());
		while (!cleanupStack.isEmpty()) {
			try {
				cleanupStack.pop().execute();
			} catch (Exception e) {
				log.info("CleanupCommand failed");
			}
		}
	}

	public void deploy(final ResourceAdapterArchive rar) {
		try {
			ZipExporter exporter = rar.as(ZipExporter.class);
			File file = tmpFolder.newFile(rar.getName());
			exporter.exportTo(file, true);
			final String appName = deployer.deploy(file);
			log.info("Application {} deployed as {}", rar.getName(), appName);
			register(new CleanupCommand() {

				@Override
				public void execute() {
					undeploy(appName);

				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Deployment failed", e);
		}
	}

	public void undeploy(final String appName) {
		log.info("Undeploy {}", appName);
		try {
			deployer.undeploy(appName);
		} catch (GlassFishException e) {
			throw new RuntimeException("Undeploy failed");
		}
	}

	public void createConnectorConnectionPool(final String raname,
			final Class<?> connectionDefinition, final String poolName,
			final Properties connectionConfigProperties) {
		log.info("Create connection pool");
		commandRunner.run("create-connector-connection-pool", "--raname="
				+ raname,
				"--connectiondefinition=" + connectionDefinition.getName(),
				poolName);
		if (connectionConfigProperties != null) {
			for (Map.Entry<Object, Object> entry : connectionConfigProperties
					.entrySet()) {
				createConnectorConnectionPoolProperty(poolName,
						(String) entry.getKey(), (String) entry.getValue());
			}
		}
		register(new CleanupCommand() {

			@Override
			public void execute() {
				deleteConnectorConnectionPool(poolName);

			}
		});
	}

	private void register(CleanupCommand cleanupCommand) {
		cleanupStack.push(cleanupCommand);
	}

	public void createConnectorConnectionPoolProperty(String poolName,
			String key, String value) {
		log.info("Create connection pool property");
		String cmd = String.format(
				"domain.resources.connector-connection-pool.%s.property.%s=%s",
				poolName, key, value);
		commandRunner.run("set", cmd);

	}

	public void createConnectorResource(final String poolName,
			final String jndiName) {
		log.info("Create connection resource");
		commandRunner.run("create-connector-resource",
				"--poolname=" + poolName, jndiName);
		register(new CleanupCommand() {

			@Override
			public void execute() {
				deleteConnectorResource(jndiName);

			}
		});
	}

	public void deleteConnectorResource(String jndiName) {
		log.info("delete connetor resource");
		commandRunner.run("delete-connector-resource", jndiName);
	}

	public void deleteConnectorConnectionPool(String poolName) {
		log.info("delete connector connection pool");
		commandRunner.run("delete-connector-connection-pool", poolName);
	}

	@SuppressWarnings("unchecked")
	public <T> T lookup(String jndiName) {
		try {
			return (T) new InitialContext().lookup(jndiName);
		} catch (NamingException e) {
			throw new RuntimeException("Lookup failed", e);
		}
	}

	abstract public static class CleanupCommand {
		abstract public void execute();
	}
}
