package com.jjh.foohang.spot.dto;

import lombok.Data;

@Data
public class SidoGugun {
    //시 도
    private int     sidoCode;
    private String  sidoName;
    private String  sidoImage;

    //구 군
    private int     gugunCode;
    private String  gugunName;

    //etc
    private int     contentTypeId; //관광지 유형
}
