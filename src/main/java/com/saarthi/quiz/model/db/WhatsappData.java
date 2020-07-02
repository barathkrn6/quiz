package com.saarthi.quiz.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "whatsapp_data")
public class WhatsappData {
    private static final long serialVersionUID = -3009157732242241608L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date_posted")
    @JsonProperty("date_posted")
    private Date datePosted;

    @Column(name = "time_posted")
    @JsonProperty("time_posted")
    private Time timePosted;

    @Column(name = "posted_by")
    @JsonProperty("posted_by")
    private String postedBy;

    @Column(name = "message")
    @JsonProperty("message")
    private String message;

    @Column(name = "group_name")
    @JsonProperty("group_name")
    private String group_name;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Time getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Time timePosted) {
        this.timePosted = timePosted;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhatsappData that = (WhatsappData) o;
        return datePosted.equals(that.datePosted) &&
                timePosted.equals(that.timePosted) &&
                postedBy.equals(that.postedBy) &&
                message.equals(that.message) &&
                group_name.equals(that.group_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datePosted, timePosted, postedBy, message, group_name);
    }
}
