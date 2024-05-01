package com.example.service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addMessage(Message newMessage) {
        messageRepository.save(newMessage);
    }

    public List<Message> getMessages() {
        return (List<Message>) messageRepository.findAll(); // I remembered to cast, yay
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public boolean deleteMessage(Integer messageId) {
        try {
            messageRepository.deleteById(messageId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean patchMessage(Integer messageId,
                                Integer postedBy,
                                String messageText,
                                Long timePostedEpoch) throws ResourceNotFoundException {
        try {
            Message patchedMessage = messageRepository.findById(messageId).orElseThrow(() ->
                    new ResourceNotFoundException("Message with Id " + messageId + " not found"));
            if (postedBy > 0) patchedMessage.setPostedBy(postedBy);
            if (timePostedEpoch > 0) patchedMessage.setTimePostedEpoch(timePostedEpoch);
            messageRepository.save(patchedMessage);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
