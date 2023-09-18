package me.CopperV.GitHub.GitHubProject.Services.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import me.CopperV.GitHub.GitHubProject.Models.BranchModel;
import me.CopperV.GitHub.GitHubProject.Models.RepositoryModel;
import me.CopperV.GitHub.GitHubProject.Models.ResponseErrorModel;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubBranchReadModel;
import me.CopperV.GitHub.GitHubProject.Models.DTO.GitHubRepoReadModel;
import me.CopperV.GitHub.GitHubProject.Services.IRepoService;
import me.CopperV.GitHub.GitHubProject.Tools.GitHubAPITool;

@Component
public class SpringRepoService implements IRepoService {
	
	@Autowired
	GitHubAPITool githubApi;
	
	@Override
	public Object getUserRepos(String user) {
		ResponseEntity<?> response = githubApi.getGitHubResponse("https://api.github.com/users/{user}/repos", String.class, user);

		Gson gson = new Gson();
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			ResponseErrorModel model = gson.fromJson((String) response.getBody(), ResponseErrorModel.class);
			model = ResponseErrorModel.builder()
					.status(response.getStatusCode().value())
					.message(model.getMessage())
					.build();
			return model;
		}
		
		GitHubRepoReadModel[] readModel = gson.fromJson((String) response.getBody(), GitHubRepoReadModel[].class);
		@SuppressWarnings("unchecked")
		List <RepositoryModel> model = Arrays.asList(readModel)
				.stream()
				.map(repo -> {
					Object branches = getRepoBranches(user, repo.getName());
					if(branches instanceof List) {
						branches = ((List<GitHubBranchReadModel>) branches)
								.stream()
								.map(branch -> {
									return BranchModel.builder()
											.branchName(branch.getName())
											.lastCommit(branch.getCommit().getSha())
											.build();
								})
								.collect(Collectors.toList());
					}
					return RepositoryModel.builder()
							.branches(branches)
							.repository(repo.getName())
							.owner(repo.getOwner().getLogin())
							.build();
				})
				.collect(Collectors.toList());
		return model;
	}

	

	@Override
	public Object getRepoBranches(String user, String repository) {
		ResponseEntity<?> response = githubApi.getGitHubResponse("https://api.github.com/repos/{user}/{repo}/branches",
				String.class,
				user, repository);

		Gson gson = new Gson();
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			ResponseErrorModel model = gson.fromJson((String) response.getBody(), ResponseErrorModel.class);
			model = ResponseErrorModel.builder()
					.status(response.getStatusCode().value())
					.message(model.getMessage())
					.build();
			return model;
		}
		
		GitHubBranchReadModel[] readModel = gson.fromJson((String) response.getBody(), GitHubBranchReadModel[].class);
		return Arrays.asList(readModel);
	}

}
