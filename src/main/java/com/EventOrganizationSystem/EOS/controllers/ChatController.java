package com.EventOrganizationSystem.EOS.controllers;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @CrossOrigin
    @GetMapping("/api/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "tell me a dad joke") String message){
        return chatClient.prompt()
                .system("You can only generate newsletters, if someone asks anything unrelated tell that you know only how to generate newsletters")
                .user(message)
                .call()
                .content();
    }
}
