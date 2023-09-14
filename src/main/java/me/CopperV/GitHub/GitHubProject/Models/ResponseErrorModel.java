package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class ResponseErrorModel extends AModel {

	private int status;
	private String message;
	
}
