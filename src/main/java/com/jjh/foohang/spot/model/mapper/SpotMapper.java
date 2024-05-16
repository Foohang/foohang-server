package com.jjh.foohang.spot.model.mapper;

import com.jjh.foohang.spot.dto.SidoGugun;
import com.jjh.foohang.spot.dto.Spot;

import java.util.List;

public interface SpotMapper {

    //시도리스트 전체 조회
    List<SidoGugun> getSidoList();

    //시도리스트 이름으로로 검색
    List<SidoGugun> findSidoBySidoName(String sidoName);

    //시도리스트 시도코드로 검색
    SidoGugun findSidoBySidoCode(int sidocode);

    //구군리스트 시도코드로 조회
    List<SidoGugun> getGugunList(int sidoCode);

    //관광명소 조회 //여기서 시도, 구군, contentType 입력받은 내용으로 조회
    List<Spot> getSpotList(SidoGugun sidoGugun);

    //관광명소 상세조회
    Spot getSpotDetail(int contentId);

}
