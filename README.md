# 개인 프로젝트 - 게시판

## 목차
- [들어가며](#들어가며)
  - [프로젝트 소개](#1-프로젝트-소개)
  - [프로젝트 기능](#2-프로젝트-기능)
  - [사용 기술](#3-사용-기술)
    - [백엔드](#3-1-백엔드)
    - [프론트엔드](#3-2-프론트엔드)
  - [실행 화면](#4-실행-화면)

   
- [프로젝트 구조](#프로젝트-구조)
  - [DB 설계](#1-DB-설계)
  - [API 설계](#2-API-설계)   

## 들어가며
### 1. 프로젝트 소개

기본적인 CRUD를 구현하여 게시판을 직접 만들어보기 위해 시작하였습니다

### 2. 프로젝트 기능

현재 백엔드와 프론트엔드의 작업이 모두 완료된 주요 기능은 다음과 같습니다
- **게시판 -** 게시글CRUD, 댓글CRUD, 조회수, 게시글 전체조회 및 상세조회, 추천하기
- **사용자 -** 회원가입, 로그인, 회원정보 수정, 이메일로 비밀번호 찾기, 회원탈퇴(스케쥴러)
  
### 3. 사용 기술

#### 3-1 백엔드

##### 기술 및 언어
- Java
- Gradle
- SpringBoot
- JPA
- MySQL
- Swagger

###### 개발툴
- VSCODE
- DBEAVER
- Intellij

#### 3-2 프론트엔드

##### 기술 및 언어
- Html, CSS
- JavaScript
- Vue.js

##### 개발툴
- VSCODE

### 4. 실행 화면
  <details>
    <summary>회원 관련</summary>
 <img width="328" alt="로그인" src="https://github.com/armycar/personal_project_board/assets/118254103/daeef99c-7efc-4306-a2ae-6d31abff1bb9">
    
  <img width="250" alt="회원가입" src="https://github.com/armycar/personal_project_board/assets/118254103/0480a657-dc5e-4e2c-9612-2bf7ed177271"> 

  <img width="316" alt="비밀번호찾기" src="https://github.com/armycar/personal_project_board/assets/118254103/98c8d418-8ba0-4a52-b8c3-62951a28b926">

<img width="186" alt="마이페이지" src="https://github.com/armycar/personal_project_board/assets/118254103/5547311c-b650-4cc0-8731-6dce3bfb4e7f">

<img width="249" alt="회원정보변경" src="https://github.com/armycar/personal_project_board/assets/118254103/254a9ab0-4fb0-47ac-9c5e-057f81883e5e">
  </details>
  <br>
  <details>
    <summary>게시판 관련</summary>
    <img width="1267" alt="메인" src="https://github.com/armycar/personal_project_board/assets/118254103/199befdf-073c-493e-8052-69721de73d78">
    <img width="1071" alt="게시물올리기" src="https://github.com/armycar/personal_project_board/assets/118254103/e0a6202a-d6d3-4e5c-9139-e09a0e2444e9">
<img width="1170" alt="상세보기" src="https://github.com/armycar/personal_project_board/assets/118254103/2094fc8c-4f92-4e8a-be17-a631a47a6010">
  </details>

## 프로젝트 구조
### 1. DB 설계
![image](https://github.com/armycar/personal_project_board/assets/118254103/2149da5f-5217-4137-a00a-ff0c709cc3d2)

### 2. API 설계
<img width="1093" alt="제목 없음" src="https://github.com/armycar/personal_project_board/assets/118254103/e1a5c7fa-a18d-4e0b-a6dd-e1af17206045">
<img width="1088" alt="제목 없음2" src="https://github.com/armycar/personal_project_board/assets/118254103/6593a1d2-9ef7-4fb1-ac5f-851ee624e004">
