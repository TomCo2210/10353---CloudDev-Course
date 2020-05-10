package songs;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ErrorHandlerRESTHelper implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		switch (response.getStatusCode()) {
		case NOT_FOUND:
			return true;
		case UNAUTHORIZED:

			return true;
		case BAD_REQUEST:

			return true;
		case INTERNAL_SERVER_ERROR:
			return true;
		default:
			return false;
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		switch (response.getStatusCode()) {
		case NOT_FOUND:
			throw new DataNotFoundException(response.getBody().toString());
		case UNAUTHORIZED:
			throw new CannotChangeIdException(response.getBody().toString());
		case BAD_REQUEST:
			throw new DataIncompleteException(response.getBody().toString());
		case INTERNAL_SERVER_ERROR:
			throw new IdAlreadyExistException(response.getBody().toString());
		default:

		}
	}

}
