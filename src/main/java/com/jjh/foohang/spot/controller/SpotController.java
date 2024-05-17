package com.jjh.foohang.spot.controller;

import com.jjh.foohang.spot.dto.SidoGugun;
import com.jjh.foohang.spot.dto.Spot;
import com.jjh.foohang.spot.model.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spots")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

    //시도리스트 전체조회
    @GetMapping("/sido")
    public ResponseEntity<?> getSidoList()
    {
        List<SidoGugun> sidoGugunList = spotService.getSidoList();

        if(sidoGugunList == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SidoGugun List is empty");

        return ResponseEntity.ok(sidoGugunList);
    }

    //시도리스트 이름으로 검색
/*    @GetMapping("/sido/{sido}")
    public ResponseEntity<?> findSidoBySidoName(@PathVariable String sido)
    {
        List<SidoGugun> sidoList = spotService.findSido(sido);

        if(sidoList == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SidoGugun List is empty");

        return ResponseEntity.ok(sidoList);
    }*/

    //시도리스트 시도코드로 검색
    @GetMapping("/sido/{sidoCode}")
    public ResponseEntity<?> findSidoBySidoCode(@PathVariable String sidoCode)
    {
        System.out.println(sidoCode);

        SidoGugun sido = spotService.findSido(Integer.parseInt(sidoCode));

        System.out.println("시도코드 : "+sidoCode+" 시도이름: "+sido.getSidoName());

        if(sido == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SidoGugun List is empty");

        return ResponseEntity.ok(sido);
    }

    //구군리스트 조회
    @GetMapping("/sido/gugun/{sidoCode}")
    public ResponseEntity<?> getGugunList(@PathVariable int sidoCode)
    {
        List<SidoGugun> gugunList = spotService.getGugunList(sidoCode);

        if(gugunList == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SidoGugun List is empty");

        return ResponseEntity.ok(gugunList);
    }

    //관광지 전체 조회 API
    @GetMapping("/attractions/{sidoCode}/{gugunCode}/{contentType}")
    public ResponseEntity<?> getSpotList(@PathVariable int sidoCode, @PathVariable int gugunCode, @PathVariable int contentType )
    {
        SidoGugun sidoGugunData = new SidoGugun();
        sidoGugunData.setSidoCode(sidoCode);
        sidoGugunData.setGugunCode(gugunCode);
        sidoGugunData.setContentTypeId(contentType);

        List<Spot> spotList = spotService.getSpotList(sidoGugunData);

        if(spotList == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Spot List is empty");

        return ResponseEntity.ok(spotList);
    }

    //관광지 콘텐츠 id로 상세조회
    @GetMapping("/attractions/{contentId}")
    public ResponseEntity<?> getSpotDetail (@PathVariable int contentId){

        Spot spotInfo = spotService.getSpotDetail(contentId);

        return ResponseEntity.ok(spotInfo);
    }

    //주변 맛집 위도 경도로 조회
    @GetMapping("/attractions/restaurants/{contentId}")
    public ResponseEntity<?> getAdjustRestaurant (@PathVariable int contentId)
    {
        List<Spot> RestaurantList = spotService.getAdjustResturant(contentId);

        //System.out.println(RestaurantList);

        return ResponseEntity.ok(RestaurantList);
    }
}
