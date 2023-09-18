package me.CopperV.GitHub.GitHubProject.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.CopperV.GitHub.GitHubProject.Models.AModel;

@Getter
@Builder
public class GitHubBranchReadModel extends AModel {
	
	private String name;
	private CommitBody commit;

	@Getter
	@AllArgsConstructor
	public static class CommitBody {
		private String sha;
	}
	
}
