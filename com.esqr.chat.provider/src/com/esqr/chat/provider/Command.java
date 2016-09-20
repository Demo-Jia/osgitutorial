package com.esqr.chat.provider;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.esqr.chat.api.Chat;
import com.esqr.chat.api.Message;

import osgi.enroute.debug.api.Debug;

@Component(
		property = {
				Debug.COMMAND_SCOPE + "=chat",
				Debug.COMMAND_FUNCTION + "=chat",
				Debug.COMMAND_FUNCTION + "=members",
				Debug.COMMAND_FUNCTION + "=send"
		},
		service = Command.class
)
public class Command {
	
	public String chat() {
		return 	"chat 					help\n" +
				"send <from> <to> <message>		Send a message\n" +
				"members					get a list of memebers\n";
	}
	
	public Collection<String> members() {
		
		return members.keySet().stream().sorted().collect(Collectors.toList());
	}
	
	public boolean send(String aFrom, String aTo, String aText) throws Exception {
		Chat myChat = members.get(aTo);
		
		if (myChat != null) {
			
			Message myMessage = new Message();
			
			myMessage.from = aFrom;
			myMessage.to = aTo;
			myMessage.text = aText;
			
			myChat.send(myMessage);
			return true;
		}
		return false;
	}
	
	@Reference(
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC,
			unbind = "removeChat"
	)
	void addChat(Chat aMember, Map<String,Object> aMap){
		
		String username = getUserName(aMap);
		if (username != null) {
			members.put(username, aMember);
		}
	}
	
	void removeChat(Chat aMember, Map<String, Object> aMap) {
		String username = getUserName(aMap);
		if (username != null) {
			members.remove(aMember);
		}
		
	}
	
	private String getUserName(Map<String,Object> aMap) {
		return (String)aMap.get(Chat.USER_NAME);
	}
	
	private Map<String, Chat> members = new ConcurrentHashMap<>();

}
