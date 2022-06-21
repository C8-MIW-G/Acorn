package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * @author Wicher Vos Feat. Thijs van Blanken
 * User model
 *
 */
@Entity @Getter @Setter
public class AcornUser implements UserDetails {

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

    public AcornUser() {
    }

    public AcornUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // Dit is een Spring ding. ROLE_ is een beschermde tekst. dit is een simple autority. iedereen die is ingelogd heeft iig de rol user.
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
