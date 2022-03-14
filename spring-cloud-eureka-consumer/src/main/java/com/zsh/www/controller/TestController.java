package com.zsh.www.controller;

import com.zsh.www.service.InterfaceTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @description
 * @date
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    InterfaceTestService testService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumerServiceByFeignTest")
    public String consumerServiceTestByFeign() {
        return testService.getProviderService();
    }

    @GetMapping("/consumerServiceByRibbonTest")
    public String consumerServiceTesByRibbon() {
        return restTemplate.getForObject("http://zsh-provider/test/getProviderStringService", String.class);
    }
}
