package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @Column(name = "messageId")
    @Id
    @GeneratedValue
    private Integer messageId;

    @Column(name = "postedBy")
    private Integer postedBy;

    @Column(name = "messageText")
    private String messageText;

    @Column(name = "timePostedEpoch")
    private Long timePostedEpoch;

    public Message() {
    }

    public Message(Integer postedBy, String messageText, Long timePostedEpoch) {
        this.postedBy = postedBy;
        this.messageText = messageText;
        this.timePostedEpoch = timePostedEpoch;
    }

    public Message(Integer messageId, Integer postedBy, String messageText, Long timePostedEpoch) {
        this.messageId = messageId;
        this.postedBy = postedBy;
        this.messageText = messageText;
        this.timePostedEpoch = timePostedEpoch;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getTimePostedEpoch() {
        return timePostedEpoch;
    }

    public void setTimePostedEpoch(Long timePostedEpoch) {
        this.timePostedEpoch = timePostedEpoch;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (messageId == null) {
            if (other.messageId != null)
                return false;
        } else if (!messageId.equals(other.messageId))
            return false;
        if (messageText == null) {
            if (other.messageText != null)
                return false;
        } else if (!messageText.equals(other.messageText))
            return false;
        if (postedBy == null) {
            if (other.postedBy != null)
                return false;
        } else if (!postedBy.equals(other.postedBy))
            return false;
        if (timePostedEpoch == null) {
            if (other.timePostedEpoch != null)
                return false;
        } else if (!timePostedEpoch.equals(other.timePostedEpoch))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", postedBy=" + postedBy +
                ", messageText='" + messageText + '\'' +
                ", timePostedEpoch=" + timePostedEpoch +
                '}';
    }

}
