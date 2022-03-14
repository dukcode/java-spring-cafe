package com.kakao.cafe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class LoginControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LoginService service;

    MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @DisplayName("로그인 요청 정보의 비밀번호와 실제 사용자의 비밀번호가 일치하면 세션에 사용자 정보를 저장한 후 사용자 목록 페이지로 이동한다.")
    @Test
    void loginSuccess() throws Exception {
        // given
        LoginParam loginParam = new LoginParam("userId", "password");
        User user = new User(1, "userId", "password", "name", "email");
        given(service.checkInfo(ArgumentMatchers.refEq(loginParam))).willReturn(user);

        // when
        mvc.perform(post("/login").params(convertToMultiValueMap(loginParam)).session(session))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        // then
        User sessionUser = (User) session.getAttribute("userInfo");
        assertThat(sessionUser).usingRecursiveComparison().isEqualTo(user);

        verify(service).checkInfo(ArgumentMatchers.refEq(loginParam));
    }

    @DisplayName("로그인 요청 정보의 비밀번호와 실제 사용자의 비밀번호가 일치하지 않으면 user/login_failed.html 을 읽어온다.")
    @Test
    void loginFail() throws Exception {
        // given
        LoginParam loginParam = new LoginParam("userId", "Inconsistency");
        User user = new User(1, "userId", "password", "name", "email");
        given(service.checkInfo(ArgumentMatchers.refEq(loginParam))).willReturn(user);

        // when
        mvc.perform(post("/login").params(convertToMultiValueMap(loginParam)).session(session))
                .andExpectAll(
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );

        // then
        User sessionUser = (User) session.getAttribute("userInfo");
        assertThat(sessionUser).isNull();

        verify(service).checkInfo(ArgumentMatchers.refEq(loginParam));
    }

    private MultiValueMap<String, String> convertToMultiValueMap(Object obj) {
        Map<String, String> map = new ObjectMapper().convertValue(obj, new TypeReference<>() {
        });
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(map);

        return params;
    }
}