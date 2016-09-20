package com.esqr.chat.provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.esqr.chat.api.Chat;
import com.esqr.chat.api.Message;

@Designate(ocd=Configuration.class,factory=false)
@Component(
		name="com.esqr.chat",
		property = {"user.name=osgi",
				"service.exported.interfaces=*"
		}
	)
public class ChatImpl implements Chat {

	@Override
	public boolean send(Message aMessage) throws Exception {
		System.out.printf("%s: %s%n", aMessage.from, aMessage.text);
		return true;
	}

}
