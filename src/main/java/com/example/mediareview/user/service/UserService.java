package com.example.mediareview.user.service;

import com.example.mediareview.user.dto.UserRequest;
import com.example.mediareview.user.dto.UserResponse;
import com.example.mediareview.user.entity.User;
import com.example.mediareview.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        User user = new User(
                request.getUsername(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse login(UserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new UserResponse(user);
    }
}