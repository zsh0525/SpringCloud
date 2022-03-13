package com.zsh.www.controller;

import com.zsh.www.result.ResponseResult;
import com.zsh.www.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @description
 * @date
 */
@RestController
@RequestMapping("/test")
public class TestService {
    @GetMapping("/getProviderStringService")
    public String testProviderService() {
        return "providerServiceTest";
    }

    @GetMapping("/getProviderRestService")
    public ResponseResult getRestServiceTest() {
        return new ResponseResult(ResultCodeEnum.SUCCESS, new Object());
    }

}
