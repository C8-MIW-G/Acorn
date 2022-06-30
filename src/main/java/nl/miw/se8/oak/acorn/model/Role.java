package nl.miw.se8.oak.acorn.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Author: Thijs van Blanken
 * Created on: 27-6-2022
 * Describes the roles a user can have
 */
@Entity
public class Role {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<AcornUser> users;

    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}