package com.ms.graphql.repo;

import java.util.List;
import com.ms.graphql.entity.Exam;
import com.ms.graphql.entity.Question;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<Question,Integer>{

	public List<Question> findAllByExam(Exam exam);

	List<Question> findAll();

	public List<Question> findAllByQuestionIdIn(List<Integer> questionIds);

	public Question findByQuestionId(Integer questionId);


}
