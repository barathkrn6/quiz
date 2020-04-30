package com.saarthi.quiz.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/*
 * Created by Barath.
 */

@Entity
@Table(name = "questions")
public class Questions implements Serializable {
    private static final long serialVersionUID = -3009157732242241605L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "options")
    private String options;

    @Column(name = "correct_option")
    @JsonProperty("correct_option")
    private Integer correctOption;

    @Column(name = "quiz_id")
    @JsonProperty("quiz_id")
    private Integer quizId;

    @Column(name = "points")
    private Integer points;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOptions() {
        return options;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public Integer getQuiz() {
        return quizId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setCorrectOption(Integer correctOption) {
        this.correctOption = correctOption;
    }

    public void setQuiz(Integer quizId) {
        this.quizId = quizId;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
