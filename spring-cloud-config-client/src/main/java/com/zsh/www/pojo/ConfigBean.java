package com.zsh.www.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author
 * @description
 * @date
 */
public class ConfigBean {
    @Value("${name}")
    private String name;

    public String getName() {
        return this.name;
    }
}
