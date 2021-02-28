package com.skdvp.app.service;

import com.skdvp.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {


    User findByUsername(String name);

    /*==========================CRUD=================================*/

    List<User> showAllUsers();

    Optional<User> showUser(Long id);

    User saveUser(User user, Long[] idRole);

    void removeUserById(Long id);


}
