package com.brtvsk.lab4.repository.auth;

import com.brtvsk.lab4.model.auth.UserRoleEntity;
import com.brtvsk.lab4.model.auth.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    UserRoleEntity getUserRoleEntityByRole(final Role role);
}
