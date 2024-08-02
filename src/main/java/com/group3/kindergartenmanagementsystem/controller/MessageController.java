package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.MessageDTO;
import com.group3.kindergartenmanagementsystem.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {
    MessageService messageService;
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<List<MessageDTO>> getAllMessageBetweenTwoUser(@PathVariable Integer id){
        return ResponseEntity.ok(messageService.getAllMessageBetweenTwoUser(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<MessageDTO>> getNewestMessage(){
        return ResponseEntity.ok(messageService.getNewestMessage());
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<MessageDTO> createNewMessage(@PathVariable(value = "id") Integer receiveUserId,
                                                       @RequestBody MessageDTO messageDTO){
        return new ResponseEntity<>(messageService.createNewMessage(receiveUserId, messageDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer id){
        messageService.deleteMessageById(id);
        return ResponseEntity.ok(String.format("Delete %s successfully", id));
    }
}
