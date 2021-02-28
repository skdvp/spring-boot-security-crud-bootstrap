package com.skdvp.app.controller;

import com.skdvp.app.model.Role;
import com.skdvp.app.model.User;
import com.skdvp.app.service.RoleService;
import com.skdvp.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class IndexController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public IndexController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String login() {
        return "redirect:/login";
    }

    /*====================SIMPLE USER - ONLY ROLE_USER=========================*/

    @GetMapping(value = "/user")
    public String getSimpleUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("currentUser", user);
        return "pages/index-user";
    }



    /*===================USER with ROLE_ADMIN + (ROLE_USER)==============*/



    /*--------------------------------INDEX---------------------------------*/


    @GetMapping(value = "/admin")
    public String index(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("listUsers", userService.showAllUsers());
        model.addAttribute("userEmpty", new User());
        model.addAttribute("roles_list", roleService.getAllRoles());
        return "pages/index-admin";
    }

    /*--------------------------------CRUD---------------------------------*/

    @PostMapping(value = "/admin/save")
    public String save(User user, @RequestParam(value = "rolesIdSelect") Long[] idRole) {
        // POST  создаём новую запись
        userService.saveUser(user, idRole);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/edit/{userId}")
    // PATCH был
    public String edit(Model model, User user, @PathVariable("userId") Long id, @RequestParam(value = "rolesIdSelect") Long[] idRole) {
//        model.addAttribute("userById", userService.showUser(id).get());
        model.addAttribute("roles_list", roleService.getAllRoles());
        Set<Role> rolesSet = new HashSet<>();
        for (Long roleId : idRole) {
            rolesSet.add(roleService.getRoleById(roleId));
        }
        user.setRoles(rolesSet);
        userService.saveUser(user, idRole);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/delete/{id}")
    // DELETE был HTML форма удаления записи
    public String delete(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    /*===================USER with ROLE_ADMIN + (ROLE_USER)==============*/

}
