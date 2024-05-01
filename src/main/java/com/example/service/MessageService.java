package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        messageRepository.deleteById(messageId);
        return false;
    }



}
