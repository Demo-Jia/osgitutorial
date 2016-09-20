package com.esqr.chat.provider;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="ESQR Chat configuration")
@interface Configuration {
  @AttributeDefinition(name="Username")
  String user_name() default "osgi";
}