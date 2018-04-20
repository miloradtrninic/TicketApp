package com.msmisa.TicketApp.events;

import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.User;

public class OnReservationCompleteEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private Locale locale;
	private Reservation reservation;
	private List<User> invitedUsers;

	public OnReservationCompleteEvent(String appUrl, Locale locale, Reservation reservation, List<User> invitedUsers) {
		super(reservation);
		this.appUrl = appUrl;
		this.locale = locale;
		this.reservation = reservation;
		this.invitedUsers = invitedUsers;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<User> getInvitedUsers() {
		return invitedUsers;
	}

	public void setInvitedUsers(List<User> invitedUsers) {
		this.invitedUsers = invitedUsers;
	}
}
