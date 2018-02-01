package com.msmisa.TicketApp.security;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    
    private final UserDetailsCustom user;

    public JwtAuthenticationResponse(String token, UserDetailsCustom user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

	public UserDetailsCustom getUser() {
		return user;
	}
    
}
