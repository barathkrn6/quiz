package com.saarthi.quiz.model.db;

/*
 * Created by Barath.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "quiz_schedule")
public class QuizSchedule implements Serializable {
    private static final long serialVersionUID = -3009157732242241607L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "quiz_id")
    @JsonProperty("quiz_id")
    private Integer quizId;

    @Column(name = "token")
    private String token;

    @Column(name = "chat_id")
    @JsonProperty("chat_id")
    private String chatId;

    @Column(name = "schedule_date")
    @JsonProperty("schedule_date")
    private Date scheduledDate;

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
