package com.quizsystem.quizsystem.service;

import com.quizsystem.quizsystem.entity.QuestionEntity;
import com.quizsystem.quizsystem.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;
    @Override
    public QuestionEntity createQuestion(QuestionEntity question) {
        return questionRepository.save(question);
    }

    @Override
    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<QuestionEntity> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public QuestionEntity updateQuestion(Long id, QuestionEntity question) throws ChangeSetPersister.NotFoundException {
        Optional<QuestionEntity> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()){
            QuestionEntity updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion);
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }
    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    @Override
    public List<QuestionEntity> getQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
