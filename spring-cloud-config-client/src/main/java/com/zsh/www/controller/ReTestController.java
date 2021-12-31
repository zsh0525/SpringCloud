package com.zsh.www.controller;

import com.zsh.www.pojo.ConfigBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @description
 * @date
 */
@RestController
@RefreshScope
@RequestMapping("/refresh")
public class ReTestController {
    @Value("${name}")
    private String name;

    @GetMapping("/getProperties")
    public String getPropertiesInfo() {
        return name;
    }
}
