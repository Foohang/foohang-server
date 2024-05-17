package com.jjh.foohang.spot.model.service;

import com.jjh.foohang.spot.dto.SidoGugun;
import com.jjh.foohang.spot.dto.Spot;

import java.util.List;

public interface SpotService {

    //시도리스트 전체 조회
    List<SidoGugun> getSidoList();

    //시도리스트 시도로 검색
    List<SidoGugun> findSido(String sidoName);

    //시도리스트 시도코드로 검색
    SidoGugun findSido(int sidocode);

    //구군리스트 조회
    List<SidoGugun> getGugunList(int sidoCode);

    //관광명소 조회 //여기서 시도, 구군, contentType 입력받은 내용으로 조회
    List<Spot> getSpotList(SidoGugun sidoGugun);

    //관광명소 상세조회
    Spot getSpotDetail(int contentId);

    //위도 경도 기준 인접한 식당 조회
    List<Spot> getAdjustResturant(int contentId);
}
