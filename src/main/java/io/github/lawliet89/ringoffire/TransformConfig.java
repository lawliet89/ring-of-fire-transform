package io.github.lawliet89.ringoffire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class TransformConfig {

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel output() {
        return new DirectChannel();
    }

    @Autowired
    TextTransformer transformer;
}
