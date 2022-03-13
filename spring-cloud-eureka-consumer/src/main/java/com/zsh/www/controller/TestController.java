package com.zsh.www.controller;

import com.zsh.www.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @description
 * @date
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping("/consumerService")
    public String consumerServiceTest() {
        return testService.getProviderService();
    }

}
