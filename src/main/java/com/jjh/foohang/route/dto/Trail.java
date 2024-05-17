package com.jjh.foohang.route.dto;

import lombok.Data;

@Data
public class Trail {
    //장소를 등록할 때 추가되는 목록

    private int id;                     //순서를 관리하는 id (auto increment)
    private int travelId;               //경로 카드 하나당 만들어지는 id
    private int contentId;              //관광지 id
    private int mealType;               //식사 타입 0: 아침 1: 점심: 2: 저녁
    private int main_accommodations;    //숙소인지 여부
}
