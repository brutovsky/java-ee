package com.brtvsk.lab4.model.auth;

import javax.persistence.*;

import com.brtvsk.lab4.model.auth.type.Permission;
import com.brtvsk.lab4.model.auth.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", unique = true)
    private Permission permission;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "rolePermissions")
    Set<UserRoleEntity> roles;

}