package com.jjh.foohang.main.interceptor;

import com.jjh.foohang.main.jwtUtil.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {

        //단순 조회 요청과 preflight 요청인 경우, true 로 넘김
        String method = request.getMethod();
        log.debug("AuthInterceptor()의 preHandle실행 method:{}", method);
        if(method.equals("GET") || method.equals("OPTIONS")) return true;

        String tokenHeader = request.getHeader("Authorization");	//Header에서 토큰 정보 추출
        //토큰 헤더가 없거나 Bearer로 시작하지 않는 경우
        if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }
        //토큰이 유효하지 않은 경우
        String token = tokenHeader.substring(7);
        if(!jwtUtil.isValid(token) ) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }

        //토큰이 유효한 경우
        return true;

    }


}
