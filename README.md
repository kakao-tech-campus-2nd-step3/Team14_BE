# Team14_BE

14조 백엔드

## 프로젝트 소개
- **프로젝트 명**: 요기먹때
- **프로젝트 기간**: 24.09 ~ 24.11
- **프로젝트 목표**: 높아진 배달 요구 금액에 대한 부담을 모르는 사람과 함께 주문하여 해결한다.

> 모르는 사람과 함께 배달을 시켜보세요!

![image](https://github.com/user-attachments/assets/0d40077d-5791-4a3d-981d-8c2726c2083f)

- 🙋🏻‍♀️배달 가격이 부담스러운 사람을 대상으로
- 👩‍👦함께 배달 음식을 주문할 수 있는 사람을 매칭해
- 💵배달 최소 주문 금액 부담을 덜고 추가적인 배달음식비 또는 배달팁을 줄여보세요!

## 배포 링크

✅FE: https://team14-fe.vercel.app/  
✅BE: https://order-together.duckdns.org/api/v1

## 주요 기능
### 1️⃣ 카카오 소설 로그인 기능
| <img src="https://github.com/user-attachments/assets/b0cd3118-7f61-4fcd-b179-02b9e92a7b24" width=300> | - 카카오 로그인을 통한 회원등록 절차 간소화 |
|-------------------------------------------------------------------------------------------------------|-----------------------|

### 2️⃣ 주문 결제(토스 페이먼츠 API)
| <img src="https://github.com/user-attachments/assets/307c2341-0379-4ba6-acc4-6235477f1912" width=300> | - 토스페이먼츠 API 를 사용한 포인트 충전 시스템<br/>-요기먹때에서 사용되는 재화를 충전할 수 있습니다. |
|-------------------------------------------------------------------------------------------------------|------------------------------------------------------------|

### 3️⃣ SMS 보내기 기능
| <img src="https://github.com/user-attachments/assets/44da63a7-4a6a-4e82-9004-3a367d038590" width=300> | - 문자로 참여할 배민 함께주문 링크를 받을 수 있습니다. |
|------------------------------------------------------------------------------------------------------|----------------------------------|

### 4️⃣ 카카오 지도 API 
| <img src="https://github.com/user-attachments/assets/ad48f508-3df6-4145-8e9c-a3c9c361396c" width=300> | - 카카오 지도를 통해 참여할 스팟을 확인할 수 있습니다. |
|------------------------------------------------------------------------------------------------------|----------------------------------|


## ERD 이미지

![image](https://github.com/user-attachments/assets/59b8b750-ceb3-4484-a538-acd53d7fc370)

## 개발 인원 : 7명

| 이름   | 담당 역할 및 기능                                                      |
| ------ | ---------------------------------------------------------------------- |
| 강호정 | <img src="https://img.shields.io/badge/-FE-blue"> 마이페이지           |
| 서민지 | <img src="https://img.shields.io/badge/-FE-blue"> 스팟(메인)페이지     |
| 임지환 | <img src="https://img.shields.io/badge/-FE-blue"> 로그인 및 결제페이지 |
| 나제법 | <img src="https://img.shields.io/badge/-BE-red"> 결제 API              |
| 서영우 | <img src="https://img.shields.io/badge/-BE-red"> 로그인 API, 회원 API  |
| 안재영 | <img src="https://img.shields.io/badge/-BE-red"> SMS API, 결제내역 API |
| 유보민 | <img src="https://img.shields.io/badge/-BE-red"> 지도(스팟) API        |

## ⚒️ BE 기술 스택
| **Category**   | **Technology**                     |
|----------------|------------------------------------|
| **Language**   | Java 21                            |
| **Framework**  | Spring Boot 3.3.3, Spring Data JPA |
| **Database**   | MySQL 8.0                          |
| **Infra**      | Amazon Web Service                 |
| **Testing**    | JUnit5, Mockito                    |

