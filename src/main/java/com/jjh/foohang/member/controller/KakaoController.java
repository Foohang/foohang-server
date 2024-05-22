package com.jjh.foohang.member.controller;

import com.jjh.foohang.main.service.MainService;
import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class KakaoController {

    private final MemberService service;
    private final MainService mainService;

    @GetMapping("")
    public ResponseEntity<?> kakao(@RequestParam("code") String code) throws URISyntaxException {
        String restApiKey = "ebf2da93e7122fee5947e193d7330814";
        String redirectUri = "http://localhost/oauth";
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        // 요청으로 엑세스 토큰 꺼내기
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> tokenBody = new LinkedMultiValueMap<>();
        tokenBody.add("grant_type", "authorization_code");
        tokenBody.add("client_id", restApiKey);
        tokenBody.add("redirect_uri", redirectUri);
        tokenBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> tokenRequestEntity = new HttpEntity<>(tokenBody, tokenHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> tokenResponse = restTemplate.exchange(tokenUrl, HttpMethod.POST, tokenRequestEntity, String.class);

        try {
            // 엑세스 토큰 꺼내기
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode tokenJsonNode = objectMapper.readTree(tokenResponse.getBody());
            String accessToken = tokenJsonNode.get("access_token").asText();

            // 엑세스 토큰으로 검색
            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.add("Authorization", "Bearer " + accessToken);
            userInfoHeaders.add("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> userInfoBody = new LinkedMultiValueMap<>();
            userInfoBody.add("property_keys", "[\"kakao_account.profile\"]");

            HttpEntity<MultiValueMap<String, String>> userInfoRequestEntity = new HttpEntity<>(userInfoBody, userInfoHeaders);

            ResponseEntity<String> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.POST, userInfoRequestEntity, String.class);

            // 결과값 꺼내오기
            JsonNode userInfoJsonNode = objectMapper.readTree(userInfoResponse.getBody());
            long id = userInfoJsonNode.get("id").asLong();
            String nickname = userInfoJsonNode.get("kakao_account").get("profile").get("nickname").asText();

            Member member = new Member();
            member.setPassword(String.valueOf(id));
            member.setEmail(String.valueOf(id));
            // id로 이메일 비번 설정
            String token = service.login(member);

            URI redirectUriWithParams;
            if (token == null) {
                // 로그인 실패 시
                String redirectUrl = "http://localhost:5173/regist/" + id + "/" + nickname;
                redirectUriWithParams = new URI(redirectUrl);
            } else {
                // 로그인 성공 시
                String redirectUrl = "http://localhost:5173/login/" + token;
                redirectUriWithParams = new URI(redirectUrl);
            }

            // 리다이렉트
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUriWithParams);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing response");
        }
    }
}
