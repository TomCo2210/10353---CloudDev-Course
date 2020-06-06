package demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessagingContoller {
	private AsyncMessageSender sender;
	
	@Autowired
	public void setSender(AsyncMessageSender sender) {
		this.sender = sender;
	}
	
	@RequestMapping(path="/send",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> sendMessageAsynchronousely (
			@RequestBody MessageBoundary message){
		this.sender
			.sendMessage(message);
		return Collections.singletonMap("message", "message was sent successfully");
	}
}
