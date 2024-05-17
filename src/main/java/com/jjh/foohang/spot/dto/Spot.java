package com.jjh.foohang.spot.dto;

import lombok.Data;

/*
contentType Id 참고

 <option value="0" selected>전체</option>
           <option value="12">관광지</option>
              <option value="14">문화시설</option>
              <option value="15">축제공연행사</option>
              <option value="25">여행코스</option>
              <option value="28">레포츠</option>
              <option value="32">숙박</option>
              <option value="38">쇼핑</option>
              <option value="39">음식점</option>
 */

@Data
public class Spot {
    private int     contentId;      //콘텐츠 id, Join용
    private int     contentTypeId;  //관광지 정보를 유추할 수 있는 ID
    private String  title;          //관광지명
    private String  addr1;          //관광지 상세 주소
    private String  firstImage;     //관광지 사진 URL1
    private String  firstImage2;    //관광지 사진 URL2
    private int     readCount;      //조회수
    private double  latitude;       //위도
    private double  longitude;      //경도

    private double  distance;        //거리
    private String  overview;       //관광지 설명, 따로 가져와야됨

    private int     mealType;       //식사 타입 0: 아침 1: 점심: 2: 저녁
    private int     main_accommodations;    //숙소여부,
}
