package com.essenstack.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * REST controller for streaming AI chat responses.
 *
 * <p>Exposes endpoints to interact with the ChatClient and receive
 * streaming responses in real-time.</p>
 */
@RestController
@RequestMapping("/api")
public class StreamController {

    /**
     * ChatClient used to communicate with the AI model.
     */
    private final ChatClient chatClient;

    /**
     * Constructor-based dependency injection for ChatClient.
     *
     * @param chatClient configured ChatClient bean
     */
    public StreamController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Streams chat responses from the AI model based on user input.
     *
     * <p>This endpoint accepts a message as a query parameter and returns
     * a reactive stream ({@link Flux}) of response chunks as they are generated.</p>
     *
     * <p><b>Example:</b> GET /api/stream?message=Hello</p>
     *
     * @param message user input message
     * @return a {@link Flux} stream of response content
     */
    @GetMapping("/stream")
    public Flux<String> chat(@RequestParam("message") String message) {

        // Build and execute the prompt with user input, then stream the response
        return chatClient
                .prompt()        // initialize prompt
                .user(message)  // set user message
                .stream()       // enable streaming response
                .content();     // extract response content as Flux<String>
    }
}