# 2022 Java Spring Cafe

2022년도 마스터즈 멤버스 백엔드 스프링 카페 프로젝트

---

# Step 1
- [x] 회원 가입
- [x] 회원 목록
- [x] 회원 프로필


## 홈

### 홈 접속
- GET 요청으로 "/"이 맵핑되면, `home`이 반환되고 브라우저 상에는 `home.html`이 렌더링됨.

## 회원 가입

### 회원가입 폼
- GET 요청으로 "/users/new"가 맵핑되면, `/users/createUserForm`이 반환되고 브라우저 상에는 회원 가입 폼이 렌더링 됨.
- 회원 가입 폼에서 submit 버튼을 누르면 폼의 userEmail, userName, password를 POST 메서드로 전송

### 회원 가입 수행
- POST 요청으로 "/users/new"가 맵핑되면, HTML 폼의 userEmail, userName, password를 `@ModelAttribute`를 통해 객체 맵핑
- userRepository에 요청하여 회원가입을 수행하고 가입이 정상적으로 수행되면 "/users"로 리다이렉트됨

### 회원 목록
- GET 요청으로 "/users"가 맵핑되면 모든 회원목록을 조회하여 Model에 담고 "/users/userList"를 반환함.
- "/users/userList"가 브라우저 상에 렌더링됨
- 회원 목록이 출력된다.

### 회원 프로필
- 회원 목록에서 회원명을 클릭하면 "/users/{userName}"을 요청함(+ url로 접근 가능)
- GET 요청으로, profile 메서드가 호출되어, 회원을 userName으로 찾아 모델에 담음
- "/users/userProfile"이 반환되어 브라우저상에 렌더링됨.
- 회원 프로필이 출력된다.

---