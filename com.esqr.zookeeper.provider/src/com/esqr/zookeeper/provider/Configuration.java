package com.esqr.zookeeper.provider;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Zookeeper client configuration")
@interface Configuration {

	@AttributeDefinition(name="Zookeeper URL", 
		description = "Use this field to specify which zookeeper instance to connect to.")
	String zookeeper_url() default "localhost:6789";
}
