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
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping("/")
    ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.create(request));

        return apiResponse;
    }

    @PutMapping("{id}")
    public UserResponse update(@PathVariable String id, @RequestBody UserUpdateRequest request){
        return userService.update(id, request);
    }

    @GetMapping("/")
    public List<UserResponse> getAll(){
        return userService.getAll();
    }

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable String id){
        return userService.getById(id);
    };

    @DeleteMapping("{id}")
    public String delete(@PathVariable String id){
        userService.getById(id);
        return "Deleted successful";
    };
}
