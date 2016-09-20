package com.esqr.zookeeper.provider;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;

import osgi.enroute.debug.api.Debug;
@Designate(ocd=Configuration.class)
@Component(
	property = { 
		Debug.COMMAND_SCOPE + "=zk",
		Debug.COMMAND_FUNCTION + "=zk",
		Debug.COMMAND_FUNCTION + "=ls",
		Debug.COMMAND_FUNCTION + "=data"
	},
	service = Command.class
)
public class Command {

	public String zk() {
		return 
			"zk	help\n" +
			"ls	<path>	list childern\n" +
			"data <path> show data of node\n";
	}
	
	@Activate
	void activate(Configuration aConfig) throws IOException {
		this.zk = new ZooKeeper(aConfig.zookeeper_url(), 10000, null);
	}


	@Deactivate
	void deactivate(BundleContext aContext) throws IOException, InterruptedException {
		
		this.zk.close();
		this.zk = null;
	}

	public List<String> ls(String aPath) throws Exception {
		return zk.getChildren(aPath,  null);
	}


	public String data(String aPath) throws Exception {
		
		byte[] data = zk.getData(aPath, false, null);
		return new String(data, "UTF-8");
	}

	String zookeeper_url;
	private ZooKeeper zk;
	
}
