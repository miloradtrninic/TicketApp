package com.msmisa.TicketApp.beans;

import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;


@Entity
@DiscriminatorValue("theatre")
public class Theatre extends Auditorium {
	private Set<Play>  playsRepertory;

	@OneToMany(mappedBy="theatre")
	public Set<Play> getPlaysRepertory() {
		return playsRepertory;
	}

	public void setPlaysRepertory(Set<Play> playsRepertory) {
		this.playsRepertory = playsRepertory;
	}

	
	
	
}
