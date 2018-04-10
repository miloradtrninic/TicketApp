package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="USER_ROLE")
public class UserRole {
	private Integer id;
	private String name;
	private Set<Privilege> privileges;
	private Set<User> users;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(value=CascadeType.ALL)
	@JoinTable(name = "PRIVILEGE_ROLE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID"))
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	@ManyToMany(mappedBy="userRoles")
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
	
}
