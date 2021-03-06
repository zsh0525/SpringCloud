package com.zsh.www.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @description
 * @date
 */
@FeignClient("zsh-provider")
public interface InterfaceTestService {
    @GetMapping("/test/getProviderStringService")
    String getProviderService();
}
