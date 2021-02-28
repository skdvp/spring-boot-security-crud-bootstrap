package com.skdvp.app.service;

import com.skdvp.app.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void saveRole(Role role);

    void deleteRoleById(Long id);

}
