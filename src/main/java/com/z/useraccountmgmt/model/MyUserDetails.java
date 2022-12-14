package com.z.useraccountmgmt.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
        /**
     *
     */
    private static final long serialVersionUID = 560L;
    
    private String email;
    private String password;
    private User user;
    private List<GrantedAuthority> authorities;
    
    public MyUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user = user;
        this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    }

    public MyUserDetails() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
