package com.monconcours.backend.controller;

import com.monconcours.backend.entity.Question;
import com.monconcours.backend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public List<Question> obtenirToutesLesQuestions() {
        return questionService.obtenirToutesLesQuestions();
    }

    @GetMapping("/questions/{id}")
    public Optional<Question> obtenirQuestionParId(@PathVariable Integer id) {
        return questionService.obtenirQuestionParId(id);
    }

    @PostMapping("/questions")
    public Question ajouterQuestion(@RequestBody Question question) {
        return questionService.ajouterQuestion(question);
    }

    @PutMapping("/questions/{id}")
    public Question modifierQuestion(@PathVariable Integer id,
                                     @RequestBody Question question) {
        return questionService.modifierQuestion(id, question);
    }

    @DeleteMapping("/questions/{id}")
    public void supprimerQuestion(@PathVariable Integer id) {
        questionService.supprimerQuestion(id);
    }
}