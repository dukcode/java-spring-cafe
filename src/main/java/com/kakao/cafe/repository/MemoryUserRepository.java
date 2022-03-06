package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static ArrayList<User> store = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return store.stream().filter(user -> user.getUserId().equals(id)).findFirst();
    }
}
