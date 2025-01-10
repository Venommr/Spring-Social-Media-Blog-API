package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().isBlank()){
            throw new IllegalArgumentException("Message text cannot be blank");
        }

        if(message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message must be less thatn 255 characters");
        }

        if(message.getPostedBy() == null || accountRepository.findById(message.getPostedBy()).isEmpty()){
            throw new IllegalArgumentException("User does not exist in database");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByUser(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }

    //return 1 if it deleted right
    public int deleteMessageById(Integer messageId){
        if(messageRepository.findById(messageId).isEmpty()){
            return 0;
        }
        messageRepository.deleteById(messageId);
        return 1;
    }

    public int updateMessageText(Integer messageId, String newMessageText){
        if(newMessageText == null || newMessageText.isBlank()){
            throw new IllegalArgumentException("Message cannot be blank");
        }
        if(newMessageText.length() > 255){
            throw new IllegalArgumentException("Message cannot be over 255 characters long");
        }
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("Message not found"));
        if(message == null){ //uneeded code since we now throw exception
            return 0;
        }
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }

    public Message getMessageById(Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }


}
