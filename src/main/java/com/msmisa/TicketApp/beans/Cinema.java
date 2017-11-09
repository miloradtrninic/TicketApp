package com.msmisa.TicketApp.beans;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@SecondaryTable(name="CINEMAS", catalog="isadb")
public class Cinema extends Auditorium {
	private List<Movie> moviesRepertory;
	
	@ManyToMany(mappedBy="cinemaList")
	@Cascade(value=CascadeType.ALL)
	public List<Movie> getMoviesRepertory() {
		return moviesRepertory;
	}

	public void setMoviesRepertory(List<Movie> moviesRepertory) {
		this.moviesRepertory = moviesRepertory;
	}
	
	
}
