package com.msmisa.TicketApp.dto.preview;

import java.util.List;

public class ProjectionPreviewDTO {
	private Integer id;
	private String naziv;
	private Integer ratings;
	private List<ActorPreviewDTO> actors;
	private List<GenrePreviewDTO> genres;
	private DirectorPreviewDTO director;
	private Integer durationMinutes;
	private String coverPath;
	private String description;
	
	private List<TerminPreviewDTO> projectionTime;
	
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
	public Integer getRatings() {
		return ratings;
	}
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}
	public List<ActorPreviewDTO> getActors() {
		return actors;
	}
	public void setActors(List<ActorPreviewDTO> actors) {
		this.actors = actors;
	}
	public List<GenrePreviewDTO> getGenres() {
		return genres;
	}
	public void setGenres(List<GenrePreviewDTO> genres) {
		this.genres = genres;
	}
	public DirectorPreviewDTO getDirector() {
		return director;
	}
	public void setDirector(DirectorPreviewDTO director) {
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
	public List<TerminPreviewDTO> getProjectionTime() {
		return projectionTime;
	}
	public void setProjectionTime(List<TerminPreviewDTO> projectionTime) {
		this.projectionTime = projectionTime;
	}
	
	
}
