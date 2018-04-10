package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.beans.movie.Genre;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class ProjectionCreationDTO {
	private String naziv;
	private Integer ratings;
	
	@ForeignKeyDTO(clazzFK=Actor.class)
	private List<Integer> actorIds;
	@ForeignKeyDTO(clazzFK=Genre.class)
	private List<Integer> genreIds;
	@ForeignKeyDTO(clazzFK=Director.class)
	private String director;
	private Integer durationMinutes;
	private String coverPath;
	private String description;
	
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
	public List<Integer> getActors() {
		return actorIds;
	}
	public void setActors(List<Integer> actors) {
		this.actorIds = actors;
	}
	public List<Integer> getGenres() {
		return genreIds;
	}
	public void setGenres(List<Integer> genres) {
		this.genreIds = genres;
	}
	
	public List<Integer> getActorIds() {
		return actorIds;
	}
	public void setActorIds(List<Integer> actorIds) {
		this.actorIds = actorIds;
	}
	public List<Integer> getGenreIds() {
		return genreIds;
	}
	public void setGenreIds(List<Integer> genreIds) {
		this.genreIds = genreIds;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
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
	
	
}
