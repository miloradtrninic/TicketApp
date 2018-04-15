package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.beans.movie.Genre;

@Entity
@Table(name = "PROJECTION")  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "entityType",discriminatorType = DiscriminatorType.STRING)
public abstract class Projection {
	private Integer id;
	private String name;
	private List<FanItem> fanItemList;
	private Integer ratings;
	private Set<Actor> actors;
	private Set<Genre> genres;
	private Director director;
	private Integer durationMinutes;
	private String coverPath;
	private String description;

	private Set<Termin> projectionTime;
	private Set<Ticket> ticketList;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
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
	@OneToMany(mappedBy="projection", fetch=FetchType.EAGER)
	@JsonIgnore
	public List<FanItem> getFanItemList() {
		return fanItemList;
	}
	public void setFanItemList(List<FanItem> fanItemList) {
		this.fanItemList = fanItemList;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name="PROJECTIONS_ACTORS", joinColumns=@JoinColumn(name="PROJECTION_ID"), inverseJoinColumns=@JoinColumn(name="ACTOR_ID"))
	public Set<Actor> getActors() {
		return actors;
	}
	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name="PROJECTIONS_GENRES", joinColumns=@JoinColumn(name="PROJECTION_ID"), inverseJoinColumns=@JoinColumn(name="GENRE_ID"))
	public Set<Genre> getGenres() {
		return genres;
	}
	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DIRECTOR_ID", nullable=false)
	public Director getDirector() {
		return director;
	}
	public void setDirector(Director director) {
		this.director = director;
	}
	public Integer getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(mappedBy="projection", fetch=FetchType.EAGER)
	@JsonIgnore
	public Set<Termin> getProjectionTime() {
		return projectionTime;
	}
	public void setProjectionTime(Set<Termin> projectionTime) {
		this.projectionTime = projectionTime;
	}
	@OneToMany(mappedBy="projection", fetch=FetchType.EAGER)
	@JsonIgnore
	public Set<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(Set<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	
	
	

}
