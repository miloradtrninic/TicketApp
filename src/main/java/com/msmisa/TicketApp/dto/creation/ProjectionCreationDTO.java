package com.msmisa.TicketApp.dto.creation;

import java.util.List;

import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.beans.movie.Genre;
import com.msmisa.TicketApp.dto.ForeignKeyDTO;

public class ProjectionCreationDTO {
	private String name;
	private Integer ratings;
	
	@ForeignKeyDTO(clazzFK=Actor.class)
	private List<Integer> actorIds;
	@ForeignKeyDTO(clazzFK=Genre.class)
	private List<Integer> genreIds;
	@ForeignKeyDTO(clazzFK=Director.class)
	private Integer directorId;
	private Integer durationMinutes;
	private String coverPath;
	private String description;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public Integer getDirectorId() {
		return directorId;
	}
	public void setDirectorId(Integer directorId) {
		this.directorId = directorId;
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
