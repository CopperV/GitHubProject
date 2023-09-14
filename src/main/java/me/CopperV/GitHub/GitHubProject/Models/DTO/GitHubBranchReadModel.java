package me.CopperV.GitHub.GitHubProject.Models.DTO;

import lombok.Getter;
import me.CopperV.GitHub.GitHubProject.Models.AModel;

@Getter
public class GitHubBranchReadModel extends AModel {
	
	private String name;
	private CommitBody commit;

	@Getter
	public class CommitBody {
		private String sha;
	}
	
}
