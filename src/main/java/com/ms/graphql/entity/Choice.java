package com.ms.graphql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter @Setter 
@Entity(name="choice")
public class Choice {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer choiceId;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="questionId", referencedColumnName="questionId")})
	private Question question;
	
	@NonNull
	private String choiceName;
	@NonNull
	private String choice;

}
