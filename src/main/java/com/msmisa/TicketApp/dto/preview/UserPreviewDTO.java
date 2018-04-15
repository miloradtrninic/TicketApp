package com.msmisa.TicketApp.dto.preview;

import java.util.List;

public class UserPreviewDTO {
	private Integer id;
	private String username;
	private String email;
	private String name, lastname;
	private String phoneNo;
	private boolean enabled;
	private String membershipName;
	private List<UserRolePreview> userRoles;
	private String city;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getMembershipName() {
		return membershipName;
	}
	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}
	public List<UserRolePreview> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRolePreview> userRoles) {
		this.userRoles = userRoles;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
