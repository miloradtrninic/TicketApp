package com.msmisa.TicketApp.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsCustom implements org.springframework.security.core.userdetails.UserDetails {
	
	private static final long serialVersionUID = 8325535401455891040L;

	@JsonIgnore
	private String password;
	
	private String username;
	private List<SimpleGrantedAuthority> grantedAuthorities;
	
	public UserDetailsCustom(String username, String password, List<SimpleGrantedAuthority> grantedAuthorities) {
		super();
		this.password = password;
		this.username = username;
		this.grantedAuthorities = grantedAuthorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
