package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(Source.class) // Source is an output channel
public class AsyncMessageSenderImpl implements AsyncMessageSender{
	private Source source;
	
	@Autowired
	public AsyncMessageSenderImpl(Source source) {
		super();
		this.source = source;
	}


	@Override
	public void sendMessage(MessageBoundary message) {
		if (!this.source
			.output()
				.send(MessageBuilder
						.withPayload(message)
						.build())) {
			throw new RuntimeException("could not send message to messaging platform");
		}
		
	}

}
