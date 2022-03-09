package com.kakao.cafe.unit.service.mock;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserForm;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService mock 단위 테스트")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User.Builder()
            .userId("userId")
            .password("userPassword")
            .name("userName")
            .email("user@example.com")
            .build();
    }

    @Test
    @DisplayName("회원가입하면 저장소에 저장된다")
    public void registerTest() {
        // given
        UserForm userForm = new UserForm("userId", "userPassword", "userName", "user@example.com");

        given(userRepository.save(any()))
            .willReturn(user);

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        User savedUser = userService.register(userForm);

        // then
        then(savedUser.getUserId()).isEqualTo("userId");
        then(savedUser.getPassword()).isEqualTo("userPassword");
        then(savedUser.getName()).isEqualTo("userName");
        then(savedUser.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("유저가 회원가입할 때 이미 있는 유저 아이디면 예외를 반환한다")
    public void registerValidationTest() {
        // given
        UserForm userForm = new UserForm("userId", "otherPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.register(userForm));

        // when, then
        then(throwable)
            .isInstanceOf(DuplicateException.class)
            .hasMessage(ErrorCode.DUPLICATE_USER.getMessage());
    }

    @Test
    @DisplayName("모든 유저를 조회한다")
    public void findUsersTest() {
        // given
        given(userRepository.findAll())
            .willReturn(List.of(user));

        // when
        List<User> users = userService.findUsers();

        // then
        then(users).containsExactlyElementsOf(List.of(user));
    }

    @Test
    @DisplayName("유저 아이디를 입력해 유저를 조회한다")
    public void findUserTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        User findUser = userService.findUser("userId");

        // then
        then(findUser).isEqualTo(user);
    }

    @Test
    @DisplayName("등록되지 않은 유저 아이디로 유저를 조화하면 예외를 반환한다")
    public void findUserExceptionTest() {
        // given
        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.findUser(any()));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("변경할 유저 정보를 입력하면 저장소의 유저 정보를 변경한다")
    public void updateUserTest() {
        // given
        UserForm userForm = new UserForm("userId", "userPassword", "otherName",
            "other@example.com");

        User changedUser = new User.Builder()
            .userNum(1)
            .userId("userId")
            .password("userPassword")
            .name("other")
            .email("other@example.com")
            .build();

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        given(userRepository.save(any()))
            .willReturn(changedUser);

        // when
        User updatedUser = userService.updateUser(userForm);

        then(updatedUser.getUserId()).isEqualTo("userId");
        then(updatedUser.getPassword()).isEqualTo("userPassword");
        then(updatedUser.getName()).isEqualTo("other");
        then(updatedUser.getEmail()).isEqualTo("other@example.com");
    }

    @Test
    @DisplayName("유저 정보 변경 시 변경할 유저가 존재하지 않으면 예외를 반환한다")
    public void updateUserNotFoundTest() {
        // given
        UserForm userForm = new UserForm("otherId", "userPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userForm));

        // then
        then(throwable)
            .isInstanceOf(NotFoundException.class)
            .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 유저 아이디가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectUserIdTest() {
        // given
        UserForm userForm = new UserForm("otherId", "userPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userForm));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

    @Test
    @DisplayName("유저 정보 변경 시 비밀번호가 일치하지 않으면 예외를 반환한다")
    public void updateUserIncorrectPasswordTest() {
        // given
        UserForm userForm = new UserForm("userId", "otherPassword", "otherName",
            "other@example.com");

        given(userRepository.findByUserId(any()))
            .willReturn(Optional.of(user));

        // when
        Throwable throwable = catchThrowable(() -> userService.updateUser(userForm));

        // then
        then(throwable)
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage(ErrorCode.INCORRECT_USER.getMessage());
    }

}
