package com.dacchub.backend.auth.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dacchub.backend.user.entity.User;
import com.dacchub.backend.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByName(username);

    if (user == null) {
      throw new UsernameNotFoundException("Username not found: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
        user.getName(),
        user.getPassword(),
        Collections.emptyList());
  }

}
