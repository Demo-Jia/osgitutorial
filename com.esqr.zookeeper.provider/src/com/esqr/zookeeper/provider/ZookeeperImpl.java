package com.esqr.zookeeper.provider;

import java.io.File;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component
public class ZookeeperImpl extends ZooKeeperServerMain {
	
	@Activate
	void activate(BundleContext aContext) {
		System.out.println("Starting Zookeeper instance");
		File myDirectory = aContext.getDataFile("zookeeper");

		config = new ServerConfig();
		config.parse(new String[] {"6789", myDirectory.getAbsolutePath()});

		zooKeeperThread = new Thread(this::zk, "com.esqr.zookeeper");
		zooKeeperThread.start();
	}
	
	@Deactivate
	void deactivate(BundleContext aContext) {
		System.out.println("Stopping Zookeeper instance");
		
		shutdown();

		zooKeeperThread.interrupt();
	}
	
	public void zk() {

		try {
			runFromConfig(config);
		} catch(Exception anException) {
			anException.printStackTrace();
		}
		
		System.out.println("Zookeeper is exiting");
	}
	
	private Thread zooKeeperThread;

	private ServerConfig config;
}