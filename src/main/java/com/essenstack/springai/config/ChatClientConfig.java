package com.essenstack.springai.config;


import com.essenstack.springai.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for {@link ChatClient}.
 *
 * <p>Creates and configures a {@link ChatClient} bean with default prompts and advisors.</p>
 *
 * <p>Includes a logging advisor for request/response tracing and sets default
 * system and user prompts to have a consistent behavior across the application.</p>
 */

@Configuration
public class ChatClientConfig {

    /**
     * Creates a configured {@link ChatClient} instance.
     *
     * <p>The client is initialized with:</p>
     * <ul>
     *   <li>A default system prompt defining the assistant's role and scope</li>
     *   <li>A default user prompt for fallback scenarios</li>
     *   <li>A {@link SimpleLoggerAdvisor} for logging interactions</li>
     * </ul>
     *
     * @param chatClientBuilder the builder used to construct the {@link ChatClient}
     * @return a fully configured {@link ChatClient} bean
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {

        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help\s
                        employees with questions related to HR policies, such as\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\s
                        kindly inform them that you can only assist with queries related to\s
                        HR policies.
                        """)
                .defaultUser("How can you help me ?")
                .build();
    }
}
