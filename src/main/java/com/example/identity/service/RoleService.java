package com.example.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identity.dto.request.RoleRequest;
import com.example.identity.dto.response.RoleResponse;
import com.example.identity.mapper.RoleMapper;
import com.example.identity.repository.PermissionRepository;
import com.example.identity.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));

        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
