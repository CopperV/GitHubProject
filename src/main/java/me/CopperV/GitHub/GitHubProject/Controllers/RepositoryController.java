package me.CopperV.GitHub.GitHubProject.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import me.CopperV.GitHub.GitHubProject.Models.ResponseErrorModel;
import me.CopperV.GitHub.GitHubProject.Services.IRepoService;

@RestController
@RequestMapping(path = "/api")
public class RepositoryController {

	@Autowired
	private IRepoService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getUserRepositoriesByJSON(
			@RequestParam(defaultValue = "") String username,
			@RequestHeader(HttpHeaders.ACCEPT) String accept) {
		
		Object model = service.getUserRepos(username);
		if(model == null) {
			ResponseErrorModel toReturn = ResponseErrorModel.builder()
					.status(500)
					.message("An error has occured in repo service")
					.build();
			return new ResponseEntity<>(toReturn, HttpStatus.valueOf(toReturn.getStatus()));
		}
		
		if(model instanceof ResponseErrorModel) {
			ResponseErrorModel error = (ResponseErrorModel) model;
			return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
		}
		
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	ResponseEntity<?> getUserRepositoriesByXML(@RequestParam(defaultValue = "") String username, @RequestHeader(HttpHeaders.ACCEPT) String accept) {
		ResponseErrorModel model = ResponseErrorModel.builder()
				.status(406)
				.message("XML is not acceptable. Please, use application/json")
				.build();
		Gson gson = new Gson();
		return new ResponseEntity<>(
				gson.toJson(model),
				HttpStatus.valueOf(model.getStatus()));
	}
	
}
