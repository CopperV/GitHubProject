package me.CopperV.GitHub.GitHubProject.Services.Impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import me.CopperV.GitHub.GitHubProject.Models.BranchModel;
import me.CopperV.GitHub.GitHubProject.Models.RepositoryModel;
import me.CopperV.GitHub.GitHubProject.Models.ResponseErrorModel;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubBranchReadModel;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubBranchReadModel.CommitBody;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubRepoReadModel;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubRepoReadModel.RepoOwner;
import me.CopperV.GitHub.GitHubProject.Tools.GitHubAPITool;

@SpringBootTest
class SpringRepoServiceTest {

	@Mock
	GitHubAPITool tool;
	
	@InjectMocks
	SpringRepoService service;
	
	static Gson gson;
	static List<GitHubBranchReadModel> githubBranches;
	static List<GitHubRepoReadModel> githubRepos;
	static List<BranchModel> branches;
	static List<RepositoryModel> repos;
	
	@SuppressWarnings("unchecked")
	@BeforeAll
	public static void init() {
		gson = new Gson();
		githubBranches = new LinkedList<>();
		githubRepos = new LinkedList<>();
		branches = new LinkedList<>();
		repos = new LinkedList<>();
		
		branches.add(BranchModel.builder()
				.branchName("main")
				.lastCommit("commit1")
				.build());
		branches.add(BranchModel.builder()
				.branchName("dev")
				.lastCommit("commit2")
				.build());
		repos.add(RepositoryModel.builder()
				.owner("CopperV")
				.repository("GitHubTestRepo")
				.branches(branches)
				.build());
		
		repos.forEach(repo -> {
			githubRepos.add(GitHubRepoReadModel.builder()
					.id(0)
					.name(repo.getRepository())
					.full_name(repo.getOwner()+"/"+repo.getRepository())
					.fork(false)
					.owner(new RepoOwner(repo.getOwner(), 0))
					.build());
			((List<BranchModel>) repo.getBranches()).forEach(branch -> {
				githubBranches.add(GitHubBranchReadModel.builder()
						.name(branch.getBranchName())
						.commit(new CommitBody(branch.getLastCommit()))
						.build());
			});
		});
	}

	@Test
	@DisplayName("Service return correct repo model")
	public void serviceReturnsCorrectRepositoryModel() throws Exception {
		when(tool.getGitHubResponse(eq("https://api.github.com/users/{user}/repos"), eq(String.class), anyString()))
			.thenReturn(ResponseEntity.ok(gson.toJson(githubRepos)));
		when(tool.getGitHubResponse(eq("https://api.github.com/repos/{user}/{repo}/branches"), eq(String.class), anyString(), anyString()))
			.thenReturn(ResponseEntity.ok(gson.toJson(githubBranches)));
		
		Object response = service.getUserRepos("CopperV");
		assertThat(response).isInstanceOf(List.class);
		
		List<RepositoryModel> model = (List<RepositoryModel>) response;
		assertThat(model.size()).isEqualTo(repos.size());
		for(int i = 0; i < model.size(); ++i) {
			assertThat(model.get(i)).isEqualTo(repos.get(i));
		}
	}

	@Test
	@DisplayName("Service return error response due to user not found")
	public void serviceReturnsErrorResponseModel() throws Exception {
		ResponseErrorModel response = ResponseErrorModel.builder()
				.status(404)
				.message("User not found")
				.build();
		
		when(tool.getGitHubResponse(eq("https://api.github.com/users/{user}/repos"), eq(String.class), anyString()))
			.thenReturn(new ResponseEntity<>(
					gson.toJson(response),
					HttpStatus.valueOf(response.getStatus())));
		
		assertThat(service.getUserRepos("CopperV")).isEqualTo(response);
	}

}
