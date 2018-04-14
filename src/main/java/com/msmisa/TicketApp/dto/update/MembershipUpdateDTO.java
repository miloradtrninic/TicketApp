package com.msmisa.TicketApp.dto.update;

import javax.persistence.Id;

public class MembershipUpdateDTO {
	@Id
	private Integer id;
	
	private Integer points;
	
	private Integer discount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	
}
