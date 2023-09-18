package me.CopperV.GitHub.GitHubProject.Tools;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import me.CopperV.GitHub.GitHubProject.Exceptions.RestTemplateResponseErrorHandler;

@Component
public class GitHubAPITool {

	private RestTemplate restTemplate = new RestTemplate();
	
	public GitHubAPITool() {
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
	}

	public <T> ResponseEntity<T> getGitHubResponse(String url, Class<T> targetClass, Object... args) {
		return restTemplate.getForEntity(url,
				targetClass,
				args);
	}
	
}
