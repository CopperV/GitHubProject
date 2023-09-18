package me.CopperV.GitHub.GitHubProject.Configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "github")
@Data
public class GithubTokenConfigurationProperties {

	private String token;
	
}
