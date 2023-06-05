package com.ms.graphql.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.graphql.entity.Exam;
import com.ms.graphql.repo.ExamRepo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;


@RestController
public class GraphQLController {

	@Autowired
	ExamRepo examRepo;
	
	@Value("classpath:exam.graphqls")
	private Resource resource;
	
	private GraphQL graphQL;
	
	@PostConstruct
	public void loadSchema() throws IOException {
		File schemaFile= resource.getFile();
		TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}
	
	private RuntimeWiring buildWiring() {
		
		DataFetcher<List<Exam>> fetcher1 = data->{
			return (List<Exam>) examRepo.findAll();
		};
		
		DataFetcher<Exam> fetcher2 = data->{
			return (Exam) examRepo.findByExamId(data.getArgument("examId"));
		};
		
		return RuntimeWiring.newRuntimeWiring().type("Query",typeWriting->
			typeWriting.dataFetcher("getAllExam", fetcher1).dataFetcher("getExamById",fetcher2)).				
				build();
		
	}

	
	
	
	@GetMapping("/exam")
	public ResponseEntity<?> getExam(){
		
		return new ResponseEntity<>(examRepo.findAll(),HttpStatus.OK);
		
	}
	
	@PostMapping("/gqexam")
	public ResponseEntity<?> getAll(@RequestBody String query){
		
		
		ExecutionResult result = graphQL.execute(query);
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}








