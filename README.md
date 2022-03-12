# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

## 프로젝트 소개
Springboot를 이용하여 간단한 게시판(카페)를 구현해보는 프로젝트입니다. 
간단한 기능인 회원가입과 글쓰기부터 구현해나갑니다.
최대한 간단한 형태로 제작해보고자 처음에는 DB도 연결하지 않은 채 동작하도록 만들어봅니다.
이후에는 H2 Database를 통해 게시글을 저장하고 가입한 회원의 정보를 등록하도록 확장해나갑니다.
DB 접근기술로는 JdbcTemplate를 활용하여 쿼리 작성을 연습해보았습니다. 
프로젝트는 배포는 Heroku로 배포할 예정입니다. 

### 배포 URL
https://codesquad-spring-cafe.herokuapp.com

## Step 03

### TO-DO
- [x] 테스트 코드 작성해보기
- [x] 코드 리뷰 피드백 사항 반영하기
- [x] 당장 필요없는 설계사항 들어내보기 (User의 id 값이라든지... )
  - userList의 #에 번호를 매기는 것을 id 없이 어떻게 보여줘야할 지 아이디어가 없어 우선 유지하기로 하였습니다.
- [x] 계층 간 데이터 모델에 대해서 학습해보기
- [x] 3단계 요구사항 적용해보기
