package com.quizsystem.quizsystem.service;

import com.quizsystem.quizsystem.entity.QuestionEntity;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    QuestionEntity createQuestion(QuestionEntity question);

    List<QuestionEntity> getAllQuestions();

    Optional<QuestionEntity> getQuestionById(Long id);

    List<String> getAllSubjects();

    QuestionEntity updateQuestion(Long id, QuestionEntity question) throws ChangeSetPersister.NotFoundException;

    void  deleteQuestion(Long id);

    List<QuestionEntity> getQuestionsForUser(Integer numOfQuestions, String subject);


}

