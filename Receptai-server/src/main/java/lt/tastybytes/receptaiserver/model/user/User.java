package lt.tastybytes.receptaiserver.model.user;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.user.FullUserDto;
import lt.tastybytes.receptaiserver.dto.user.PublicUserDto;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
import lt.tastybytes.receptaiserver.model.ManageableModel;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails, ManageableModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public String getProfileUrl() {
        return profileUrl;
    }
    public void setProfileUrl(String avatarUrl) {
        profileUrl = avatarUrl;
    }

    @Column(nullable = true)
    private String profileUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map((a) -> new SimpleGrantedAuthority(a.getName())).toList();
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        var roles = new ArrayList<>(this.roles);
        roles.add(role);
        //System.out.println(this.roles.get(0));
        //System.out.println(this.roles.get(1));
        this.roles = roles;
    }

    public PublicUserDto toPublicUserDto() {
        return new PublicUserDto(id, name, profileUrl);
    }
    public FullUserDto toFullUserDto() {
        var roles = getRoles().stream().map(Role::getName).toList();
        return new FullUserDto(id, name, email, profileUrl, roles);
    }

    public Long getId() {
        return id;
    }

    @Override
    public void assertCanBeManagedBy(@NotNull User user) throws MissingRightsException {
        if (getId().equals(user.getId())) {
            return;
        }

        // If user that is being asserted is admin and not themselves
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            // If the user that's being managed is an admin, dont allow this operation
            if (this.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new MissingRightsException("You cannot manage another administrator!");
            }

            return;
        }

        throw new MissingRightsException("You are missing the required rights to manage this user!");
    }
}