package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserCollectionRepository implements UserRepository {

    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {

        if (Objects.isNull(user.getUserNum())) {
            // persist
            user.setUserNum(users.size() + 1);
            users.add(user);
            return user;
        }
        // merge
        User findUser = findByUserId(user.getUserId())
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        findUser.update(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
            .filter(user -> user.getUserId().equals(userId))
            .findAny();
    }

    @Override
    public void deleteAll() {
        users = new ArrayList<>();
    }

}
