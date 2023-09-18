package me.CopperV.GitHub.GitHubProject.Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ResponseErrorModel extends AModel {

	private int status;
	private String message;
	
}
