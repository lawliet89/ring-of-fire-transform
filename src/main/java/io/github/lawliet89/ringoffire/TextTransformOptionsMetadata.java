package io.github.lawliet89.ringoffire;

import org.springframework.xd.module.options.spi.ModuleOption;

import javax.validation.constraints.NotNull;

public class TextTransformOptionsMetadata {
    private String suffix = " foobar";

    @ModuleOption("suffix to append to payloads")
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @NotNull
    public String getSuffix() {
        return suffix;
    }
}
