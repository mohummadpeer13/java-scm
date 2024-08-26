package com.example.demo.entities;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{
     
    @Id
    private String userId;

    @Column(nullable = false)
    private String pseudo;

    @Column(unique = true, nullable=false)
    private String email;

    @Column(nullable = false)
    @Getter(AccessLevel.NONE)
    private String password;

    @Column(length = 1000)
    private String description;

    @Column(length = 1500)
    private String userImage;

    private boolean enabled=false;
    
    private boolean emailVerified=false;
    
    private boolean phoneVerified=false;

    private String emailToken;

    //SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB 
    @Getter(value = AccessLevel.NONE)   
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    
    private String providerUserId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }
    
    @Override
    public String getUsername() {
        return this.email;
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
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    
}
