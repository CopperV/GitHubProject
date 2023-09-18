package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class BranchModel{
	
	private String branchName;
	private String lastCommit;
	
}
