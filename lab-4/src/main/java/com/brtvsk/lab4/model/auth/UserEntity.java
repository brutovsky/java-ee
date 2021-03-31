package com.brtvsk.lab4.model.auth;

import java.util.List;

import javax.persistence.*;

import com.brtvsk.lab4.model.auth.type.Role;
import lombok.*;

@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude="userRole")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id", nullable=false)
    private UserRoleEntity userRole;

}
