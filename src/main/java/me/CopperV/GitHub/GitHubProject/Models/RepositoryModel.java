package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class RepositoryModel {

	private String repository;
	private String owner;
	private Object branches;

	
}
