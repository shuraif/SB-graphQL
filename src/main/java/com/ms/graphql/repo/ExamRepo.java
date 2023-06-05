package com.ms.graphql.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.ms.graphql.entity.Exam;



public interface ExamRepo extends CrudRepository<Exam, Integer>{

	Exam findByExamId(Integer examId);
	
	List<Exam> findAll(Sort sortByIdDesc);

}
