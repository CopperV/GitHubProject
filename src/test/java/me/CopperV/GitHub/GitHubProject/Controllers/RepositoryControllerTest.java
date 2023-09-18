package me.CopperV.GitHub.GitHubProject.Controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import me.CopperV.GitHub.GitHubProject.Models.BranchModel;
import me.CopperV.GitHub.GitHubProject.Models.RepositoryModel;
import me.CopperV.GitHub.GitHubProject.Models.ResponseErrorModel;
import me.CopperV.GitHub.GitHubProject.Services.IRepoService;

@SpringBootTest
@AutoConfigureMockMvc
class RepositoryControllerTest {

	@MockBean
	IRepoService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	static List<RepositoryModel> model;
	static Gson gson;
	
	@BeforeAll
	public static void prepareModel() {
		gson = new Gson();
		model = new LinkedList<>();
		model.add(RepositoryModel.builder()
				.owner("CopperV")
				.branches(new LinkedList<BranchModel>())
				.repository("TestRepo")
				.build());
	}

	@Test
	@DisplayName("Controller return ok status with user repos")
	public void controllerReturnsUserRepositories() throws Exception {
		when(service.getUserRepos(anyString()))
			.thenReturn(model);
		
		mockMvc.perform(get("/api")
				.content("CopperV")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Controller return status code 406 with error message")
	public void controllerReturns406Code() throws Exception {
		when(service.getUserRepos(anyString()))
			.thenReturn(model);
		
		mockMvc.perform(get("/api")
				.content("CopperV")
				.contentType(MediaType.APPLICATION_XML_VALUE)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE))
		.andExpect(status().is(406));
	}

	@Test
	@DisplayName("Controller return status code 404 with error message")
	public void controllerReturns404Code() throws Exception {
		ResponseErrorModel error = ResponseErrorModel.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message("User not found")
				.build();
		when(service.getUserRepos(anyString()))
			.thenReturn(error);
		
		mockMvc.perform(get("/api")
				.content("CopperV")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is(404));
	}
	
}
