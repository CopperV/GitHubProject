package me.CopperV.GitHub.GitHubProject.Services;

import org.springframework.stereotype.Service;

@Service
public interface IRepoService {

	public Object getUserRepos(String user);
	public Object getRepoBranches(String user, String repository);
	
}
