package com.brtvsk.lab4.model.auth;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.brtvsk.lab4.model.BookEntity;
import com.brtvsk.lab4.model.auth.type.Role;
import lombok.*;

@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude="userRole")
@ToString(exclude = "userRole")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_fav_book",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<BookEntity> favBooks;

}
