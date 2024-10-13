package com.example.identity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identity.dto.request.PermissionRequest;
import com.example.identity.dto.response.PermissionResponse;
import com.example.identity.entity.Permission;
import com.example.identity.mapper.PermissionMapper;
import com.example.identity.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        //        var permissions = permissionRepository.findAll();
        //        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();

        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
