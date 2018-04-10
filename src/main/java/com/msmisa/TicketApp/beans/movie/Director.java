package com.msmisa.TicketApp.beans.movie;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msmisa.TicketApp.beans.Projection;


@Entity
@Table(name="DIRECTORS", catalog="isadb")
public class Director {
	private Integer id;
	private String name;
	private String lastName;
	private List<Projection> projectionList;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@OneToMany(mappedBy="director")
	@JsonIgnore
	public List<Projection> getProjectionList() {
		return projectionList;
	}
	public void setProjectionList(List<Projection> projectionList) {
		this.projectionList = projectionList;
	}
	
	
}
