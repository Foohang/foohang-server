package com.jjh.foohang.route.model.service;

import com.jjh.foohang.route.dto.Trail;
import com.jjh.foohang.route.dto.Travel;
import com.jjh.foohang.route.model.mapper.RouteMapper;
import com.jjh.foohang.spot.dto.Spot;
import com.jjh.foohang.spot.model.mapper.SpotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteMapper routeMapper;
    private final SpotMapper    spotMapper;

    private String sidoToString(int sidoCode) {
        String region = "";
        switch (sidoCode) {
            case 1:
                region = "서울";
                break;
            case 2:
                region = "인천";
                break;
            case 3:
                region = "대전";
                break;
            case 4:
                region = "대구";
                break;
            case 5:
                region = "광주";
                break;
            case 6:
                region = "부산";
                break;
            case 7:
                region = "울산";
                break;
            case 8:
                region = "세종특별자치시";
                break;
            case 31:
                region = "경기도";
                break;
            case 32:
                region = "강원도";
                break;
            case 33:
                region = "충청북도";
                break;
            case 34:
                region = "충청남도";
                break;
            case 35:
                region = "경상북도";
                break;
            case 36:
                region = "경상남도";
                break;
            case 37:
                region = "전라북도";
                break;
            case 38:
                region = "전라남도";
                break;
            case 39:
                region = "제주도";
                break;
        }

        return region;
    }

    @Override
    public List<Spot> shortestPath(List<Spot> spotList) {

        //TODO:: 여기에 최단 경로 알고리즘이 들어감

        //==================================

        //임시로 contentId 내림차순으로 정렬
        Collections.sort(spotList, (Spot o1, Spot o2) -> Integer.compare(o2.getContentId(), o1.getContentId()));
        return spotList;
    }

    @Override
    public int saveRoute(List<Spot> spotList, int memberId, String startDate, String endDate) {
        //1. "정렬된" spotList를 받아온다는 기준
        if(spotList==null)
        {
            System.out.println("관광지 정보를 알 수 없습니다.");
            return -1;
        }

        if(spotList.isEmpty())
        {
            System.out.println("관광지가 비어있습니다.");
            return -1;
        }

        //2. DB에서 Travel 하나 생성 -> id 받아옴
        int createTravel = routeMapper.insertTravel(memberId);
        System.out.println("여행 정보 travel 생성 : "+createTravel); //잘 생성시 1 반환

        //현재 테이블에서 최대 값을 가져옴
        int travelIndex = routeMapper.getTravelId();
        System.out.println("현재 만들어진 travelId : "+travelIndex);

        Travel travel = routeMapper.selectTravel(travelIndex);

        if(travel==null)
        {
            System.out.println("travel 생성 실패");
            return -1;
        }

        Spot firstSpot = spotList.get(0);
        Spot lastSpot = spotList.get(spotList.size()-1);

        //2-1Travel 정보를 모두 객체에 입력넣음
        travel.setStartRegion(sidoToString(firstSpot.getSidoCode()));
        travel.setEndRegion(sidoToString(lastSpot.getSidoCode()));

        travel.setStartDate(startDate);
        travel.setEndDate(endDate);

        //TODO::사진 없을 시 엑박 URL로 대체
        travel.setStartImage(firstSpot.getFirstImage());
        travel.setEndImage(lastSpot.getFirstImage());

        travel.setStartAttraction(firstSpot.getTitle());
        travel.setEndAttraction(lastSpot.getTitle());

        //3. spotList를 trail 리스트로 바꿈
        Spot tempSpot = null;
        int size = spotList.size();

        for (int i = size; i>0; i--)
        {
            tempSpot = spotList.get(i-1);
            spotList.remove(i-1);

            Trail trail = new Trail();
            trail.setTravelId(travelIndex);
            trail.setContentId(tempSpot.getContentId());
            trail.setMealType(tempSpot.getMealType());
            trail.setMainAccommodations(tempSpot.getMainAccommodations());

            //4. trail리스트를 db에 저장
            int result = routeMapper.insertTrail(trail);
            System.out.println("trailInsert : " +result);
            if(result!=1)
            {
                System.out.println("trail 일부 삽입 실패");
                routeMapper.deleteTravelByTravelId(travelIndex);
                return -1;
            }
        }

        //5. Travel 정보를 db에 저장
        int travelUpdateComplate = routeMapper.updateTravel(travel);
        System.out.println("travelUpdateComplate : " +travelUpdateComplate);
        if(travelUpdateComplate!=1)
        {
            System.out.println("노드 저장 실패");
            routeMapper.deleteTravelByTravelId(travelIndex);
            return -1;
        }

        System.out.println("노드 저장 성공");
        return travelUpdateComplate;
    }

    @Override
    public List<Travel> findTravelListByMemberId(int memberId) {
        return routeMapper.selectTravelsByMemberId(memberId);
    }

    @Override
    public List<Spot> findTrailListByTravelId(int travelId) {
        List<Trail> trailList = routeMapper.selectTrailByTravelId(travelId);

        List<Spot> spotList = new ArrayList<>();
        if(trailList!= null)
        {
            for(Trail trail : trailList)
            {
                Spot spot = spotMapper.findSpotByContentId(trail.getContentId());

                if(spot == null)
                {
                    System.out.println("Spot 데이터 손상");
                    return null;
                }
                spot.setMealType(trail.getMealType());
                spot.setMainAccommodations(trail.getMainAccommodations());

                spotList.add(spot);
            }
        }

        return spotList;
    }

    @Override
    public int deleteTravelByTravelId(int travelId) {
        return routeMapper.deleteTravelByTravelId(travelId);
    }
}
