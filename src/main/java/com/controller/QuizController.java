package com.controller;

import com.config.QuizConfig;
import com.entity.Quiz;
import com.entity.User;
import com.exception.QuizNotAvailableException;
import com.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private QuizService quizService;
    private QuizConfig reservationConfig;

    @Autowired
    public QuizController(QuizService quizService, QuizConfig quizConfig) {
        this.quizService = quizService;
        this.reservationConfig = quizConfig;
    }

    @GetMapping("/start/{subjectCode}")
    public String startQuiz(@PathVariable String subjectCode, HttpSession session) {
        Quiz quiz = quizService.getQuiz(subjectCode);

        session.setAttribute("quiz", quiz);
        session.removeAttribute("questionTimestamp");
        session.setAttribute("secPerQuestion", reservationConfig.getSecondsPerQuestion());

        return "redirect:/quiz/question";
    }

    @GetMapping("/question")
    public String getCurrentQuestion(@SessionAttribute(name = "quiz", required = false) Quiz quiz, HttpSession httpSession) {
        if (quiz == null) {
            throw new QuizNotAvailableException("W tej chwili nie trwa żaden quiz");
        }

        Instant instant = (Instant) httpSession.getAttribute("questionTimestamp");
        if (instant == null) {
            instant = Instant.now();
            httpSession.setAttribute("questionTimestamp", instant);
        }

        return "quiz";
    }

    @PostMapping("/question")
    public String saveAndNext(@SessionAttribute(name = "quiz", required = false) Quiz quiz, HttpSession httpSession, HttpServletRequest request) {
        if (quiz == null) {
            throw new QuizNotAvailableException("W tej chwili nie trwa żaden quiz");
        }

        try {
            Long id = Long.parseLong(request.getParameter("attemptedAnswerId"));
            if (quiz.getCurrentQuestion().getCorrectAnswer().getId().equals(id)) {
                quiz.addResult(quiz.getCurrentQuestion().getTotalMarks());
            }
        } catch (Exception e) { }

        int currentQuestion = quiz.getCurQuestionNumber();
        if (currentQuestion == reservationConfig.getTotalQuestions()) {
            quizService.saveQuiz(quiz);
            httpSession.removeAttribute("quiz");
            httpSession.removeAttribute("questionTimestamp");
            httpSession.removeAttribute("secPerQuestion");
            return "redirect:/quiz/result";
        }

        currentQuestion++;
        quiz.setCurQuestionNumber(currentQuestion);
        httpSession.removeAttribute("questionTimestamp");

        return "redirect:/quiz/question";
    }

    @GetMapping("/result")
    public String result(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("quizes", quizService.getQuizzesByUser(user));
        return "result";
    }
}
