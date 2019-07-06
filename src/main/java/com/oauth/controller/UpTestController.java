package com.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create QiangShW
 * @version: v1.0
 * @description: com.oauth.controller
 * @date:2019/6/24
 **/
@RestController
public class UpTestController {


    @GetMapping("/isUp")
    public String isUp(){
        return "服务已经启动";
    }
}
