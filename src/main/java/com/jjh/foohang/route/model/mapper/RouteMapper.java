package com.jjh.foohang.route.model.mapper;

import com.jjh.foohang.route.dto.Trail;
import com.jjh.foohang.route.dto.Travel;
import com.jjh.foohang.spot.dto.Spot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RouteMapper {
    //최적 경로 생성
    List<Spot> shortestPath(List<Spot> spotList);

    //여행 정보 travelID로 조회
    Travel selectTravel(int travelId);

    int getTravelId();

    //여행 정보 생성
    int insertTravel(int memberId);

    //여행 정보를 db에 저장
    int updateTravel(Travel travel);

    //경로 노드들을 db에 저장
    int insertTrail(Trail trail);

    //멤버 id로 리스트 조회
    List<Travel> selectTravelsByMemberId(int memberId);

}
