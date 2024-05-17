package com.jjh.foohang.route.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import com.jjh.foohang.route.model.service.RouteService;
import com.jjh.foohang.spot.dto.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    //최적 경로 생성 //##토큰 추가 바람
    @PostMapping("/recommendation")
    public ResponseEntity<?> recommendation(@RequestBody List<Spot> spotList
                    , @RequestHeader("Authorization") String tokenHeader)
    {
        int memberId = jwtUtil.getMemberIdFromToken(tokenHeader.substring(7));

        Member authMember = memberService.findMemberById(memberId);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("id 토큰이 일치하지 않습니다.");

        //System.out.println(spotList);
        List<Spot> shortestRouteList = routeService.shortestPath(spotList);

        return ResponseEntity.ok(shortestRouteList);
    }

    //경로 추가
    @PostMapping("/")
    public ResponseEntity<?> addRoute(@RequestBody List<Spot> spotList,
                                      @RequestHeader("Authorization") String tokenHeader)
    {
        //0.(인터셉터? 로그인 인증 받아야됨)
        int memberId = jwtUtil.getMemberIdFromToken(tokenHeader.substring(7));

        Member authMember = memberService.findMemberById(memberId);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");

        try {
            routeService.saveRoute(spotList, memberId);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("경로가 정상적으로 저장되지 않았습니다.");
        }

        return ResponseEntity.ok().build();
    }

}
