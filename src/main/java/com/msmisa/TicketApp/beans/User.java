package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private String email;
	private String password;
	private String name, lastname;
	private String phoneNo;
	private List<User> friends;
	private List<User> friendOf;
	private List<User> friendRequests;
	private List<User> friendRequestsSent;
	private List<FanAd> userAds;
	private List<Bid> bidList;
	private UserRole userRole;
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
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy="friends")
	public List<User> getFriendOf() {
		return friendOf;
	}
	public void setFriendOf(List<User> friendOf) {
		this.friendOf = friendOf;
	}
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinTable(name="FRIEND_REQUESTS", joinColumns=@JoinColumn(name="personID"), inverseJoinColumns=@JoinColumn(name="friendID"))
	public List<User> getFriendRequests() {
		return friendRequests;
	}
	public void setFriendRequests(List<User> friendRequests) {
		this.friendRequests = friendRequests;
	}
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomUserSerializer.class)
	@ManyToMany(mappedBy="friendRequests")
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<User> getFriendRequestsSent() {
		return friendRequestsSent;
	}
	
	public void setFriendRequestsSent(List<User> friendRequestsSent) {
		this.friendRequestsSent = friendRequestsSent;
	}
	
	@JsonInclude(Include.NON_NULL)
	@JsonBackReference(value="ads_users")
	@OneToMany(mappedBy="postedBy")
	public List<FanAd> getUserAds() {
		return userAds;
	}
	public void setUserAds(List<FanAd> userAds) {
		this.userAds = userAds;
	}
	
	@JsonBackReference(value="bids_user")
	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy="fromUser")
	@Cascade(value=CascadeType.ALL)
	public List<Bid> getBidList() {
		return bidList;
	}
	public void setBidList(List<Bid> bidList) {
		this.bidList = bidList;
	}
	@OneToOne
	@Cascade(value=CascadeType.ALL)
	@JoinColumn(name="ROLE", nullable=false)
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
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