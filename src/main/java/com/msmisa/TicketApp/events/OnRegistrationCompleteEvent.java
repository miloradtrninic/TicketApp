package com.msmisa.TicketApp.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.msmisa.TicketApp.beans.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private Locale locale;
	private User user;
	
	public OnRegistrationCompleteEvent(String appUrl, Locale locale, User user) {
		super(user);
		this.appUrl = appUrl;
		this.locale = locale;
		this.user = user;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
