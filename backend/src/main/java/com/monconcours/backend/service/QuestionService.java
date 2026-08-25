package com.monconcours.backend.service;

import com.monconcours.backend.entity.Question;
import com.monconcours.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question ajouterQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> obtenirToutesLesQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> obtenirQuestionParId(Integer id) {
        return questionRepository.findById(id);
    }

    public Question modifierQuestion(Integer id, Question nouvelleQuestion) {

        Question questionExistante = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question non trouvée"));

        questionExistante.setEnonce(nouvelleQuestion.getEnonce());
        questionExistante.setQcm(nouvelleQuestion.getQcm());

        return questionRepository.save(questionExistante);
    }

    public void supprimerQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
