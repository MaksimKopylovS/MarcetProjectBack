package marcet.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username_fld")
    private String username;

    @Column(name = "password_fld")
    private String password;

    @Column(name = "email_fld")
    private String mail;

    @ManyToMany
    @JoinTable(name = "users_roles_tbl",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> rolesCollection;

    @OneToMany(mappedBy = "user")
    private List<Address> listAddresses;
}
