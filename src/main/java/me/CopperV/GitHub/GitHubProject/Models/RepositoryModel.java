package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepositoryModel {

	private String repository;
	private String owner;
	private Object branches;

	
}
