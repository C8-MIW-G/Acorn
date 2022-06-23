package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.viewmodel.UserEditView;
import nl.miw.se8.oak.acorn.viewmodel.UserRegisterView;
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
 */
@Entity @Getter @Setter
public class AcornUser implements UserDetails {

    public static final int MINIMAL_EMAIL_LENGTH = 5;
    public static final int MINIMAL_PASSWORD_LENGTH = 5;

    @Id @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",  cascade = ALL)
    private List<PantryUser> pantryUsers;

    public AcornUser() {
    }

    public AcornUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AcornUser(UserRegisterView userRegisterView) {
        this.email = userRegisterView.getEmail();
        this.name = this.email;
        this.password = userRegisterView.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // Dit is een Spring ding. ROLE_ is een beschermde tekst. dit is een simple autority. iedereen die is ingelogd heeft iig de rol user.
        return authorityList;
    }

    public static boolean passwordsMatch(String password, String passwordCheck) {
        return password.equals(passwordCheck);
    }

    @Override
    public String getUsername() {
        return email;
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
