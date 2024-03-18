package com.quizsystem.quizsystem.controller;


import com.quizsystem.quizsystem.entity.QuestionEntity;
import com.quizsystem.quizsystem.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;

    @PostMapping("/create-new-question")
    public ResponseEntity<QuestionEntity> createQuestion(@Valid @RequestBody QuestionEntity question){
        QuestionEntity createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }

    @GetMapping("/all-questions")
    public ResponseEntity<List<QuestionEntity>> getAllQuestions(){
        List<QuestionEntity> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<QuestionEntity> theQuestion = questionService.getQuestionById(id);
        if (theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @PutMapping("/question/{id}/update")
    public ResponseEntity<QuestionEntity> updateQuestion(
            @PathVariable Long id, @RequestBody QuestionEntity question) throws ChangeSetPersister.NotFoundException {
        QuestionEntity updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<List<QuestionEntity>> getQuestionsForUser(
            @RequestParam Integer numOfQuestions, @RequestParam String subject){
        List<QuestionEntity> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);

        List<QuestionEntity> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(numOfQuestions, mutableQuestions.size());
        List<QuestionEntity> randomQuestions = mutableQuestions.subList(0, availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }

}
