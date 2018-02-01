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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msmisa.TicketApp.json.CustomUserSerializer;

@Entity
@Table(name="USERS")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {
	private Integer id;
	private String username;
	private String email;
	private String password;
	private String name, lastname;
	private String phoneNo;
	private Set<User> friends;
	private Set<User> friendOf;
	private Set<User> friendRequests;
	private Set<User> friendRequestsSent;
	private Set<FanAd> userAds;
	private Set<Bid> bidList;
	private Set<UserRole> userRoles;
	private Membership membership;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(nullable=false, unique=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(nullable=false, unique=true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
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
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(name="FRIENDS", joinColumns=@JoinColumn(name="personID"), inverseJoinColumns=@JoinColumn(name="friendID"))
	public Set<User> getFriends() {
		return friends;
	}
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="friends")
	public Set<User> getFriendOf() {
		return friendOf;
	}
	public void setFriendOf(Set<User> friendOf) {
		this.friendOf = friendOf;
	}
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(name="FRIEND_REQUESTS", joinColumns=@JoinColumn(name="personID"), inverseJoinColumns=@JoinColumn(name="friendID"))
	public Set<User> getFriendRequests() {
		return friendRequests;
	}
	public void setFriendRequests(Set<User> friendRequests) {
		this.friendRequests = friendRequests;
	}
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@ManyToMany(mappedBy="friendRequests")
	@LazyCollection(LazyCollectionOption.FALSE)
	public Set<User> getFriendRequestsSent() {
		return friendRequestsSent;
	}
	
	public void setFriendRequestsSent(Set<User> friendRequestsSent) {
		this.friendRequestsSent = friendRequestsSent;
	}
	
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value="ads_users")
	@OneToMany(mappedBy="postedBy")
	public Set<FanAd> getUserAds() {
		return userAds;
	}
	public void setUserAds(Set<FanAd> userAds) {
		this.userAds = userAds;
	}
	
	@JsonBackReference(value="bids_user")
	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy="fromUser")
	@Cascade(value=CascadeType.ALL)
	public Set<Bid> getBidList() {
		return bidList;
	}
	public void setBidList(Set<Bid> bidList) {
		this.bidList = bidList;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(value=CascadeType.ALL)
	@JoinTable(name="USERS_ROLES", joinColumns= @JoinColumn(name="USER_ID"), inverseJoinColumns=@JoinColumn(name = "ROLE_ID"))
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRole) {
		this.userRoles = userRole;
	}
	@OneToOne
	@Cascade(value=CascadeType.ALL)
	@JoinColumn(name="MEMBERSHIP_TYPE", nullable=false)
	public Membership getMembership() {
		return membership;
	}
	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	
	
	
}
