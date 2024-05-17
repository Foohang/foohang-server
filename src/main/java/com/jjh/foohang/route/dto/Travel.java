package com.jjh.foohang.route.dto;

import lombok.Data;

@Data
public class Travel {
    //장소를 불러올 때 나오는 정보

    private int     travelId;       //  여행 id, 이 값으로 db 조회
    private int     memberId;       //  멤버 id 정보, 이 값으로 db 조회
    private String  region;         //  지역 탭
    private String  startDate;      //  여행 시작 날짜
    private String  endDate;        //  여행 끝 날짜
    private String  startImage;     //  시작점 이미지
    private String  endImage;       //  도착점 이미지
    private String  startAttraction;//  시작점 관광지명
    private String  endAttraction;  //  도착점 관광지명
}
