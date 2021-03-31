package com.brtvsk.lab4.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.brtvsk.lab4.config.auth.MyPasswordEncoder;
import com.brtvsk.lab4.model.auth.PermissionEntity;
import com.brtvsk.lab4.model.auth.UserRegistrationForm;
import com.brtvsk.lab4.model.auth.UserEntity;
import com.brtvsk.lab4.model.auth.UserRoleEntity;
import com.brtvsk.lab4.model.auth.type.Role;
import com.brtvsk.lab4.repository.auth.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(mapAuthorities(List.copyOf(user.getUserRole().getRolePermissions())))
                .build();
    }

    private static List<GrantedAuthority> mapAuthorities(final List<PermissionEntity> permissions) {
        return permissions.stream()
                .map(PermissionEntity::getPermission)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
