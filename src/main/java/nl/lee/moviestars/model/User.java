package nl.lee.moviestars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String email;

    @OneToMany(
            targetEntity = nl.lee.moviestars.model.Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<nl.lee.moviestars.model.Authority> authorities = new HashSet<>();

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() { return enabled;}
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}
    public Set<nl.lee.moviestars.model.Authority> getAuthorities() { return authorities; }
    public void setAuthorities(Set<nl.lee.moviestars.model.Authority> authorities) { this.authorities = authorities; }

    public void addAuthority(nl.lee.moviestars.model.Authority authority) {
        this.authorities.add(authority);
    }
    public void addAuthority(String authorityString) {
        this.authorities.add(new nl.lee.moviestars.model.Authority(this.username, authorityString));
    }
    public void removeAuthority(nl.lee.moviestars.model.Authority authority) {
        this.authorities.remove(authority);
    }
    public void removeAuthority(String authorityString) {
        this.authorities.removeIf(authority -> authority.getAuthority().equalsIgnoreCase(authorityString));
    }

}