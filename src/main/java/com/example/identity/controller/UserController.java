package com.example.identity.controller;

import com.example.identity.dto.request.ApiResponse;
import com.example.identity.dto.request.UserCreationRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping
    ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.create(request));

        return apiResponse;
    }

    @PutMapping("{id}")
    ApiResponse<UserResponse> update(@PathVariable String id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder().result(userService.update(id, request)).build();
    }

    @GetMapping("/")
    ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAll())
                .build();
    }

    @GetMapping("{id}")
    ApiResponse<UserResponse> getById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder().result(userService.getById(id)).build();
    };

    @DeleteMapping("{id}")
    ApiResponse<String> delete(@PathVariable String id){
        userService.getById(id);
        return ApiResponse.<String>builder().result("Deleted successful").build();
    };
}
