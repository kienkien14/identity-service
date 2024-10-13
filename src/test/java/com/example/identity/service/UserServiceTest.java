package com.example.identity.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.exception.AppException;
import com.example.identity.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 1, 1);

        request = UserCreationRequest.builder()
                .username("snoopy")
                .firstName("snoopy")
                .lastName("nguyen")
                .password("12346789")
                .dob(dob)
                .build();

        response = UserResponse.builder()
                .id("abcxyz")
                .username("snoopy")
                .firstName("snoopy")
                .lastName("nguyen")
                .dob(dob)
                .build();

        user = User.builder()
                .id("abcxyz")
                .username("snoopy")
                .firstName("snoopy")
                .lastName("nguyen")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.create(request);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("abcxyz");
        Assertions.assertThat(response.getUsername()).isEqualTo("snoopy");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.create(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "snoopy")
    void getMyInfo_valid_success() {
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // WHEN
        var response = userService.getInfo();

        // THEN
        Assertions.assertThat(response.getUsername()).isEqualTo("snoopy");
        Assertions.assertThat(response.getId()).isEqualTo("abcxyz");
    }

    @Test
    @WithMockUser(username = "snoopy")
    void getMyInfo_userNotFound_error() {
        // GIVEN
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.getInfo());

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
