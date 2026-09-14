package com.business.erp.service;

import com.business.erp.entity.User;
import com.business.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile)
            throws UsernameNotFoundException {

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getMobile())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().toUpperCase())
                .build();
    }
}

