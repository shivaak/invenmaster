package com.application.invenmaster.controller;

import com.application.invenmaster.config.ApplicationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HelloController {

    private final ApplicationInfo applicationInfo;

    @GetMapping("hello")
    public String getHello(){
        return "Muruga from test3 " + applicationInfo.getAppName();
    }
}
