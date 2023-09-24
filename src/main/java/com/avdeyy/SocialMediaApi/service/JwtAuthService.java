package com.avdeyy.SocialMediaApi.service;

import com.avdeyy.SocialMediaApi.dto.JwtTokenRequest;
import com.avdeyy.SocialMediaApi.dto.JwtTokenResponse;
import com.avdeyy.SocialMediaApi.dto.UserCustomDto;
import com.avdeyy.SocialMediaApi.dto.UserDto;
import com.avdeyy.SocialMediaApi.entity.User;
import com.avdeyy.SocialMediaApi.exeption.AuthError;
import com.avdeyy.SocialMediaApi.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtAuthService {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtTokenRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.createToken(userDetails);
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody UserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AuthError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AuthError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createUser(registrationUserDto);
        return ResponseEntity.ok(new UserCustomDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
