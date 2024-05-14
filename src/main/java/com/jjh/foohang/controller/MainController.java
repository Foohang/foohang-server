package com.jjh.foohang.controller;

import com.jjh.foohang.dto.response.MainResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/test")
    public MainResponseDto index() {
        MainResponseDto dto = new MainResponseDto();
        dto.setId(1);
        return dto;
    }

}
