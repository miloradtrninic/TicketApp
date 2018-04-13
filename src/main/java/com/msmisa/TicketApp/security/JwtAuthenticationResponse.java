package com.msmisa.TicketApp.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.msmisa.TicketApp.beans.Membership;
import com.msmisa.TicketApp.beans.Privilege;
import com.msmisa.TicketApp.beans.UserRole;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final int id;
    private final String token;
    private final List<String> roles;
    private final List<String> privileges;

    public JwtAuthenticationResponse(int id, String token, List<String> roles, List<String> privileges) {
    	this.id = id;
        this.token = token;
        this.roles = roles;
        this.privileges = privileges;
    }

    public String getToken() {
        return this.token;
    }

	public List<String> getRoles() {
		return roles;
	}

	public List<String> getPrivileges() {
		return privileges;
	}

	public int getId() {
		return id;
	}
   
}
