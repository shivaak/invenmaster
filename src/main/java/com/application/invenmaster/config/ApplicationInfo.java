package com.application.invenmaster.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationInfo {

    @Value("${spring.application.name}")
    private String appName;

}
