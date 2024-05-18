package com.jjh.foohang.route.model.service;

import com.jjh.foohang.spot.dto.Spot;

import java.util.List;

public interface RouteService {

    //최적 경로 생성
    List<Spot> shortestPath(List<Spot> spotList);

    //경로 추가 (경로 저장)
    int saveRoute(List<Spot> spotList, int memberId, String startDate, String endDate);

}