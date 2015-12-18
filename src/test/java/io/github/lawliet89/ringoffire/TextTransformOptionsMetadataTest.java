package io.github.lawliet89.ringoffire;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.xd.module.ModuleDefinition;
import org.springframework.xd.module.ModuleDefinitions;
import org.springframework.xd.module.options.DefaultModuleOptionsMetadataResolver;
import org.springframework.xd.module.options.ModuleOption;
import org.springframework.xd.module.options.ModuleOptionsMetadata;
import org.springframework.xd.module.options.ModuleOptionsMetadataResolver;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.xd.module.ModuleType.processor;

public class TextTransformOptionsMetadataTest {
    private String moduleName = "text-transform";

    @Test
    public void testModuleProperties() {
        ModuleOptionsMetadataResolver moduleOptionsMetadataResolver = new DefaultModuleOptionsMetadataResolver();
        String resource = "classpath:/";
        ModuleDefinition definition = ModuleDefinitions.simple(moduleName, processor, resource);
        ModuleOptionsMetadata metadata = moduleOptionsMetadataResolver.resolve(definition);

        assertThat(
                metadata,
                containsInAnyOrder(moduleOptionNamed("suffix")));
    }

    public static Matcher<ModuleOption> moduleOptionNamed(String name) {
        return hasProperty("name", equalTo(name));
    }
}