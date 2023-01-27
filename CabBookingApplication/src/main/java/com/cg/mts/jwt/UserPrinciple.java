package com.cg.mts.jwt;

import com.cg.mts.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String username;
    
    @JsonIgnore
    private String password;
    
    private String role;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Integer userId, String password,String username,String role,
    		Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.role = role;
        
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
    	List<GrantedAuthority> authorities = new ArrayList();
        return new UserPrinciple(
                user.getUserId(),
                user.getPassword(),
                user.getUsername(),
                user.getRole(),
                authorities
        );
    }

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(userId, user.userId);
    }
}
