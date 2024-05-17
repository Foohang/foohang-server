package com.jjh.foohang.route.controller;

import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.route.model.service.RouteService;
import com.jjh.foohang.spot.dto.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    //private final RouteService routeService;

    //최적 경로 생성
    @PostMapping("/recommendation")
    public ResponseEntity<?> recommendation(@RequestBody List<Spot> spotList)
    {
        System.out.println(spotList);

        return ResponseEntity.ok().build();
    }

}
