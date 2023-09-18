package me.CopperV.GitHub.GitHubProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GitHubProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubProjectApplication.class, args);
	}

}
