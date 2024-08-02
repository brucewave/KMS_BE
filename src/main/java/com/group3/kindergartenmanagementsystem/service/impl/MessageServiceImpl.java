package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Message;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.MessageDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.MessageRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.MessageService;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    MessageRepository messageRepository;
    UserRepository userRepository;
    ChildRepository childRepository;
    RoleRepository roleRepository;
    SecurityService securityService;
    @Override
    public List<MessageDTO> getAllMessageBetweenTwoUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        User currentUser = securityService.getCurrentUser();
        List<Message> messages = messageRepository.findByFromUserAndToUserOrFromUserAndToUserOrderBySendTimeAsc(
                user, currentUser, currentUser, user);
        return messages.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getNewestMessage() {
        User user = securityService.getCurrentUser();
        List<Message> messages = messageRepository.findNewestMessageListByUser(user.getId());
        return messages.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public MessageDTO createNewMessage(Integer receiveUserId, MessageDTO messageDTO) {
        User receiveUser = userRepository.findById(receiveUserId).orElseThrow(()->new ResourceNotFoundException("User", "id", receiveUserId));
        User currentUser = securityService.getCurrentUser();
        if (securityService.compareRole("ROLE_PARENT")){
            Role role = roleRepository.findByRoleName("ROLE_TEACHER");
            if (!receiveUser.getRoles().contains(role))
                throw new APIException(HttpStatus.BAD_REQUEST, "Receive user is not a teacher");
            Child child = childRepository.findByParent(currentUser);
            if (!child.getTeacher().equals(receiveUser))
                throw new APIException(HttpStatus.BAD_REQUEST, "Receive teacher is not your child's teacher");
        }
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setFromUser(currentUser);
        message.setToUser(receiveUser);
        message.setSendTime(LocalDateTime.now());
        Message createdMessage = messageRepository.save(message);
        return mapToDTO(createdMessage);
    }

    @Override
    public void deleteMessageById(Integer messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(()->new ResourceNotFoundException("Message", "id", messageId));
        User sendUser = message.getFromUser();
        if (!securityService.getCurrentUser().equals(sendUser)){
            throw new APIException(HttpStatus.BAD_REQUEST, "You can not delete this message");
        }
        messageRepository.delete(message);
    }

    private MessageDTO mapToDTO(Message message){
        return MessageDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .sendTime(message.getSendTime())
                .sendUserId(message.getFromUser().getId())
                .receiveUserId(message.getToUser().getId())
                .build();
    }
}
