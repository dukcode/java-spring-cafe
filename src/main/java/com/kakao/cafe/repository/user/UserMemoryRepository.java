package com.kakao.cafe.repository.user;


import com.kakao.cafe.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserMemoryRepository implements UserRepository {

    private static List<User> store = new ArrayList<>();
    private static Long id = 1L;

    @Override
    public User save(User user) {
        user.setId(id++);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return store.stream().filter((user -> user.getId().equals(id))).findAny();

    }
}
