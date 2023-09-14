package me.CopperV.GitHub.GitHubProject.Models.DTO;

import lombok.Getter;
import me.CopperV.GitHub.GitHubProject.Models.AModel;

@Getter
public class GitHubRepoReadModel extends AModel {

	private int id;
	private String name;
	private String full_name;
	private boolean fork;
	private RepoOwner owner;
	
	@Getter
	public class RepoOwner {
		private String login;
		private long id;
	}
	
}
