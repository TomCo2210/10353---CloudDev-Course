package demo;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class InputChannelListener {
	@StreamListener(Sink.INPUT)
	public void handleInputMessageFromAsyncPlatform (MessageBoundary input) {
		System.err.println("***** maessage received: " + input.getMessage());
	}
}
