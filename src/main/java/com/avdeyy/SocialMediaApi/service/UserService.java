package com.avdeyy.SocialMediaApi.service;

import com.avdeyy.SocialMediaApi.dto.UserDto;
import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createUser(UserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of());
        return userRepository.save(user);
    }

    public ResponseEntity<?> findByPrincipal(Principal principal, Function<User, ResponseEntity<?>> found) {
        Optional<User> currentUser = Optional.ofNullable(principal)
                .map(Principal::getName)
                .flatMap(this::findByUsername);

        return currentUser.isPresent() ?
                found.apply(currentUser.get()) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public Long findByPrincipalLong(Principal principal, Function<User, Long> found) {
        Optional<User> currentUser = Optional.ofNullable(principal)
                .map(Principal::getName)
                .flatMap(this::findByUsername);

        return found.apply(currentUser.get());
    }


    public Long getFollowing(Principal principal) {
        return findByPrincipalLong(principal, currentUser ->
                Long.valueOf(userRepository.findByPostsFromFollowing(currentUser.getId())));
    }
}
