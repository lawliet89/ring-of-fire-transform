package io.github.lawliet89.ringoffire;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

public class TransformConfigTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        Properties properties = new Properties();
        properties.put("suffix", "foobar");
        context.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("options", properties));
        context.register(TextTransformer.class);
        context.register(TestConfiguration.class);
        context.refresh();

        MessageChannel input = context.getBean("input", MessageChannel.class);
        SubscribableChannel output = context.getBean("output", SubscribableChannel.class);

        final AtomicBoolean handled = new AtomicBoolean();
        output.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                handled.set(true);
                assertEquals("hellofoobar", message.getPayload());
            }
        });
        input.send(new GenericMessage<String>("hello"));
        assertTrue(handled.get());
    }

    @Configuration
    @Import(TransformConfig.class)
    static class TestConfiguration {
        @Bean
        public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
            PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new
                    PropertySourcesPlaceholderConfigurer();
            return propertySourcesPlaceholderConfigurer;
        }
    }
}