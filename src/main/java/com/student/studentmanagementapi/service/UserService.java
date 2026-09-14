package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.User;
import com.student.studentmanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.student.studentmanagementapi.dto.LoginRequest;
import com.student.studentmanagementapi.dto.LoginResponse;

import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByMobile(request.getMobile());

        if (optionalUser.isEmpty()) {
            return new LoginResponse(false, "User not found");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(false, "Invalid password");
        }

        String token = jwtService.generateToken(user.getMobile());

        return new LoginResponse(
                true,
                "Login successful",
                token
        );
    }
}