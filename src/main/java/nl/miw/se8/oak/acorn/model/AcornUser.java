package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.viewmodel.UserEditView;
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
    public static final int MINIMAL_NAME_LENGTH = 5;

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

    // TODO - FetchType EAGER is bad practice! Ask Joost!
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public AcornUser() {
    }

    public AcornUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AcornUser(UserEditView userEditView) {
        this.email = userEditView.getEmail();
        this.name = userEditView.getName();
        this.password = userEditView.getNewPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPrivileges(roles));
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return authorityList;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
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
