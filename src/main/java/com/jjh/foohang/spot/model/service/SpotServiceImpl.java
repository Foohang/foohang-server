package com.jjh.foohang.spot.model.service;

import com.jjh.foohang.spot.dto.SidoGugun;
import com.jjh.foohang.spot.dto.Spot;
import com.jjh.foohang.spot.model.mapper.SpotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {

    private final SpotMapper spotMapper;

    @Override
    public List<SidoGugun> getSidoList() {
        return spotMapper.getSidoList();
    }

    @Override
    public List<SidoGugun> findSido(String sidoName) {
        return spotMapper.findSidoBySidoName(sidoName);
    }

    @Override
    public SidoGugun findSido(int sidocode) {
        return spotMapper.findSidoBySidoCode(sidocode);
    }

    @Override
    public List<SidoGugun> getGugunList(int sidoCode) {
        return spotMapper.getGugunList(sidoCode);
    }

    @Override
    public List<Spot> getSpotList(SidoGugun sidoGugun)
    {
        int contentTypeId = sidoGugun.getContentTypeId();

        if(contentTypeId == 0)
            return spotMapper.getSpotListAll(sidoGugun);

        return spotMapper.getSpotList(sidoGugun);
    }

    @Override
    public Spot getSpotDetail(int contentId) {

        //0일시 전체조회
        if(contentId ==0)
            return spotMapper.getSpotDetailAll();

        return spotMapper.getSpotDetail(contentId);
    }

    @Override
    public List<Spot> getAdjustResturant(int contentId)
    {
        final double km = 3.0;

        Spot centerPoint = spotMapper.findSpotByContentId(contentId);
        double spotLatitude = centerPoint.getLatitude();   //위도
        double spotLongitude = centerPoint.getLongitude(); //경도

        //"모든" 식당 정보를 가져온다.
        List<Spot> RestaurantList = spotMapper.getRestaurantList();
        List<Spot> innerResturantList = new ArrayList<>();
        if(RestaurantList != null)
        {
            double latitude, longitude, distance;
            for(Spot spot : RestaurantList)
            {
                latitude = spot.getLatitude();
                longitude = spot.getLongitude();

                distance = haversine(spotLatitude, spotLongitude, latitude, longitude);

                System.out.println("거리 : "+distance);
                if(distance <= km)
                    innerResturantList.add(spot);
            }
        }

        return innerResturantList;
    }

    //위도 경도를 통해 실제 km 값을 구하기
    private double haversine(double lat1, double lon1, double lat2, double lon2)
    {
        final double R = 6371; // Radius of the earth in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // Distance in kilometers
        return distance;
    }


}
