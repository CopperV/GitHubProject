package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BranchModel{
	
	private String branchName;
	private String lastCommit;
	
}
