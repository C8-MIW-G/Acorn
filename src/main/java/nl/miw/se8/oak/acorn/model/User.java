package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Wicher Vos Feat. Thijs van Blanken
 * User model
 *
 */

@Entity @Getter @Setter
public class User {

    public static final int MINIMAL_USERNAME_LENGTH = 4;
    public static final int MINIMAL_PASSWORD_LENGTH = 6;

    @Id @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String displayName;
    private String password;

    @OneToMany(mappedBy = "user",  cascade = ALL)
    private List<PantryUser> pantryUsers;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
