package com.jjh.foohang.route.model.service;

import com.jjh.foohang.route.model.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl {

    private final RouteMapper routeMapper;

}
