package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getAllMessageBetweenTwoUser(Integer userId);
    List<MessageDTO> getNewestMessage();
    MessageDTO createNewMessage(Integer receiveUserId, MessageDTO messageDTO);
    void deleteMessageById(Integer messageId);
}
