package me.CopperV.GitHub.GitHubProject.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.CopperV.GitHub.GitHubProject.Models.AModel;

@Getter
@Builder
public class GitHubRepoReadModel extends AModel {

	private int id;
	private String name;
	private String full_name;
	private boolean fork;
	private RepoOwner owner;
	
	@Getter
	@AllArgsConstructor
	public static class RepoOwner {
		private String login;
		private long id;
	}
	
}
