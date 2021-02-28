package com.skdvp.app.service;

import com.skdvp.app.model.Role;
import com.skdvp.app.model.User;
import com.skdvp.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:

    @Override // UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }


    /*==========================CRUD=================================*/

    @Transactional
    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> showUser(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User saveUser(User user, Long[] idRole) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Set<Role> roleSet = new HashSet<>();
        for (Long id : idRole) {
            roleSet.add(roleService.getRoleById(id));
        }

        user.setRoles(roleSet);
        userRepository.save(user);
        return user;
    }


    @Transactional
    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

}
