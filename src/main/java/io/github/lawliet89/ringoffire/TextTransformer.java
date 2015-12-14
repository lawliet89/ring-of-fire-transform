package io.github.lawliet89.ringoffire;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;

@MessageEndpoint
public class TextTransformer {
    @Transformer(inputChannel = "input", outputChannel = "output")
    public String transform(String payload) {
        return payload + " foobar";
    }
}