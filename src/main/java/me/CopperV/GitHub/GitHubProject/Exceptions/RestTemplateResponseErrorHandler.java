package me.CopperV.GitHub.GitHubProject.Exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().equals(HttpStatus.NOT_FOUND);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if(!response.getStatusCode().equals(HttpStatus.NOT_FOUND))
			return;
	}

}
