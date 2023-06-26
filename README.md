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
- **게시판 -** 글쓰기, 글삭제, 조회수, 게시글 전체조회 및 상세조회
- **사용자 -** 회원가입, 로그인, 회원정보 수정, 이메일로 비밀번호 찾기, 회원탈퇴(스케쥴러)
- **댓글 -** 댓글쓰기, 댓글조회

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

  **1. 회원가입 화면**
  <img width="250" alt="회원가입" src="https://github.com/armycar/personal_project_board/assets/118254103/0480a657-dc5e-4e2c-9612-2bf7ed177271">
  회원가입을 할때 유효성 검사 및 중복확인을 수행하여 회원가입을 진행후 메인화면으로 이동한다.

  **2. 로그인 화면**
  <img width="328" alt="로그인" src="https://github.com/armycar/personal_project_board/assets/118254103/daeef99c-7efc-4306-a2ae-6d31abff1bb9">
  아이디와 비밀번호를 입력받아 로그인이 완료되면 메인화면으로 이동한다
  
  </details>

## 프로젝트 구조
### 1. DB 설계
![image](https://github.com/armycar/personal_project_board/assets/118254103/2149da5f-5217-4137-a00a-ff0c709cc3d2)

### 2. API 설계
<img width="1093" alt="제목 없음" src="https://github.com/armycar/personal_project_board/assets/118254103/e1a5c7fa-a18d-4e0b-a6dd-e1af17206045">
<img width="1088" alt="제목 없음2" src="https://github.com/armycar/personal_project_board/assets/118254103/6593a1d2-9ef7-4fb1-ac5f-851ee624e004">
