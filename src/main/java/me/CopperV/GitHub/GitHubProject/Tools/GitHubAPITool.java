package me.CopperV.GitHub.GitHubProject.Tools;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import me.CopperV.GitHub.GitHubProject.Configurations.GithubTokenConfigurationProperties;
import me.CopperV.GitHub.GitHubProject.Exceptions.RestTemplateResponseErrorHandler;

@Component
public class GitHubAPITool {

	private final GithubTokenConfigurationProperties properties;
	
	private RestTemplate restTemplate = new RestTemplate();
	private HttpEntity<?> requestEntity;
	
	public GitHubAPITool(GithubTokenConfigurationProperties properties) {
		this.properties = properties;
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		if(this.properties.getToken() != null && !this.properties.getToken().isBlank()) {
			String token = this.properties.getToken();
			headers.set("Authorization", "Bearer "+token);
		}
		System.out.println("Test1 "+headers.toString());
		
		requestEntity = new HttpEntity<>(headers);
	}

	public <T> ResponseEntity<T> getGitHubResponse(String url, Class<T> targetClass, Object... args) {
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, targetClass, args);
	}
	
}
