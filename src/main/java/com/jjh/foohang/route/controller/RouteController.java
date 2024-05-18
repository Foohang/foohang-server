package com.jjh.foohang.route.controller;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import com.jjh.foohang.route.dto.Trail;
import com.jjh.foohang.route.dto.Travel;
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

    private Member checkUser(String token)
    {
        int memberId = jwtUtil.getMemberIdFromToken(token.substring(7));

        Member authMember = memberService.findMemberById(memberId);

        if(authMember == null)
            return null;

        return authMember;
    }

    //최적 경로 생성
    @PostMapping("/recommendation")
    public ResponseEntity<?> recommendation(@RequestBody List<Spot> spotList
                    , @RequestHeader("Authorization") String tokenHeader)
    {
        Member authMember = checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("id 정보가 존재하지 않습니다.");

        //System.out.println(spotList);
        List<Spot> shortestRouteList = routeService.shortestPath(spotList);

        return ResponseEntity.ok(shortestRouteList);
    }

    //경로 추가
    @PostMapping("/{startDate}/{endDate}")
    public ResponseEntity<?> addRoute(@RequestBody List<Spot> spotList,
                                      @RequestHeader("Authorization") String tokenHeader,
                                      @PathVariable String startDate,
                                      @PathVariable String endDate)
    {
        Member authMember = checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");

            int result = routeService.saveRoute(spotList, authMember.getMemberId(), startDate, endDate);

            if(result == -1)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("경로가 정상적으로 저장되지 않았습니다.");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<?>  findTravelByMemberId(@RequestHeader("Authorization") String tokenHeader)
    {
        Member authMember = checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");

        List<Travel> travelList = routeService.findTravelListByMemberId(authMember.getMemberId());

        if(travelList == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("리스트 정보가 없습니다.");
        }

        return ResponseEntity.ok(travelList);
    }

    @GetMapping("/{travelId}")
    public ResponseEntity<?> findTrailListByTravelId(@RequestHeader("Authorization") String tokenHeader,@PathVariable int travelId)
    {
        Member authMember = checkUser(tokenHeader);

        if(authMember == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id 토큰이 일치하지 않습니다.");

        List<Trail> trailList = routeService.findTrailListByTravelId(travelId);

        if(trailList ==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("trail 리스트 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(trailList);
    }

}
