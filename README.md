# README.md

# Foohang-server

---

## IDE

- Intellij IDEA 2024.1.1 (ver 241.15989.150)

## Framework

- Spring Boot 3.2.5

### Library

- Java(TM) SE Development Kit 17.0.10 (64-bit)
- Maven
- Lombok

## DB

- MySQL 8.0.35

### ERD

## API 명세서

| Function | URL | Method |
| --- | --- | --- |
| 회원정보 조회 API | /api/members/login | POST |
| 회원가입 API | /api/members | POST |
| 회원 정보 수정 API | /api/members | PUT |
| 회원 후기 리스트 조회 API | /api/members/reviews/{email}/{region} | GET |
| 회원 후기 등록 API | /api/members/reviews | POST |
| 시도 리스트 조회 API | /api/sido/{sidoCode} | GET |
| 관광 명소 조회 API | /api/attractions/{sido}/{gugun}/{contentType} | GET |
| 주변 맛집 조회 API | /api/attractions/restaurants | GET |
| 최적 경로 생성 API | /api/routes/recommendation | POST |
| 최적 경로 등록 API | /api/routes | POST |
| 최적 경로 삭제 API | /api/routes | DELETE |

# Convention

---

## issue label

| tag name | purpose |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |

## branch convention

| tag name | purpose |
| --- | --- |
| develop | 개발 브렌치 |
| feature | 기능 개발 브렌치 ex) feature/issue번호-기능요약 |
| fix | 버그 수정 브렌치 ex) fix/issue번호-버그요약 |
| release | 배포 브렌치 ex) release/v1.0 |

## commit convention

| tag name | purpose |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Env | 개발 환경 관련 설정 |
| Style | 코드 스타일 수정(세미콜론, 인텐트 등의 스타일적인 부분만) |
| Commnet | 주석 추가/수정 |
| Docs | 내부 문서 추가/수정 |
| Test | 테스트 추가/수정 |
| Chore | 빌드 관련 코드 수정 |
| Rename | 파일 및 폴더명 수정 |
| Remove | 파일 삭제 |
| Init | 프로젝트 등록(1회성) |