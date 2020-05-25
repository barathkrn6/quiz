package com.saarthi.quiz.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "telegram_question_mapping")
public class TelegramQuestionMapping implements Serializable {
    private static final long serialVersionUID = -3009157732242241608L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "telegram_poll_id")
    @JsonProperty("telegram_poll_id")
    private String telegramPollId;

    @Column(name = "question_id")
    @JsonProperty("question_id")
    private Integer quetionId;

    @Column(name = "quiz_id")
    @JsonProperty("quiz_id")
    private Integer quizId;

    @Column(name = "chat_id")
    @JsonProperty("chat_id")
    private String chatId;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public String getTelegramPollId() {
        return telegramPollId;
    }

    public Integer getQuetionId() {
        return quetionId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public String getChatId() {
        return chatId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTelegramPollId(String telegramPollId) {
        this.telegramPollId = telegramPollId;
    }

    public void setQuetionId(Integer quetionId) {
        this.quetionId = quetionId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
