package com.msmisa.TicketApp.beans;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.beans.movie.Genre;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(name = "entityType",discriminatorType = DiscriminatorType.STRING)
public abstract class Projection {
	private Integer id;
	private String naziv;
	private Integer price;
	private List<FanItem> fanItemList;
	private Integer ratings;
	private List<Actor> actors;
	private List<Genre> genres;
	private Director director;
	private Integer durationMinutes;
	private String coverPath;
	private String description;
	private List<Hall> hallList;
	private List<Date> projectionTime;
	private List<Ticket> ticketList;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@OneToMany(mappedBy="projection")
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
	@ManyToMany(mappedBy="projectionList")
	public List<Actor> getActors() {
		return actors;
	}
	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}
	@ManyToMany
	@JoinTable(name="PROJECTIONS_GENRES", joinColumns=@JoinColumn(name="PROJECTION_ID"), inverseJoinColumns=@JoinColumn(name="GENRE_ID"))
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	@ManyToOne
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
	@ManyToMany
	@Cascade(value=CascadeType.ALL)
	@JoinTable(name="HALLS_PROJECTIONS", joinColumns=@JoinColumn(name="PROJECTION_ID"), inverseJoinColumns=@JoinColumn(name="HALL_ID"))
	public List<Hall> getHallList() {
		return hallList;
	}
	public void setHallList(List<Hall> hallList) {
		this.hallList = hallList;
	}
	@ElementCollection
	@CollectionTable(name="PROJECTION_TIME")
	public List<Date> getProjectionTime() {
		return projectionTime;
	}
	public void setProjectionTime(List<Date> projectionTime) {
		this.projectionTime = projectionTime;
	}
	@OneToMany(mappedBy="projection")
	public List<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}
	
	
	

}