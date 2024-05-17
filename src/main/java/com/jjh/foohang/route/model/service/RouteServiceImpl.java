package com.jjh.foohang.route.model.service;

import com.jjh.foohang.route.dto.Travel;
import com.jjh.foohang.route.model.mapper.RouteMapper;
import com.jjh.foohang.spot.dto.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteMapper routeMapper;

    @Override
    public List<Spot> shortestPath(List<Spot> spotList) {

        //DB에다 서비스 만들어야됨
        //spotList에서 Trail에 쓸 정보 빼옴

        /*
            private int id;                     //순서를 관리하는 id (auto increment)
            private int travelId;               //경로 카드 하나당 만들어지는 id
            private int contentId;              //관광지 id
            private int mealType;               //식사 타입 0: 아침 1: 점심: 2: 저녁
            private int main_accommodations;    //숙소인지 여부
         */

        //TODO:: 여기에 최단 경로 알고리즘이 들어감

        //==================================

        //DB에서 auto increment id를 알아냄
        //그 id의 +1의 값으로  Travel이라는 카드를 만듦

        //이걸로 Trail이라는 객체를 만듬

        //Travel routeCard = new Travel();

        //임시로 조회수 순으로 정렬
        Collections.sort(spotList, (Spot o1, Spot o2) -> Integer.compare(o1.getReadCount(), o2.getReadCount();
        return spotList;
    }
}
