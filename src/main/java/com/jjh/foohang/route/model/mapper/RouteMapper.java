package com.jjh.foohang.route.model.mapper;

import com.jjh.foohang.spot.dto.Spot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RouteMapper {
    //최적 경로 생성
    List<Spot> shortestPath(List<Spot> spotList);

}
