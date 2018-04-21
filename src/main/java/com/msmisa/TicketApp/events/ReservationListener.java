package com.msmisa.TicketApp.events;

import java.util.ArrayList;
import java.util.List;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.InvitationDao;

public class ReservationListener implements ApplicationListener<OnReservationCompleteEvent> {
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private InvitationDao invDao;
	
	private OnReservationCompleteEvent event;
	private Integer ticketIds;
	
	@Override
	public void onApplicationEvent(OnReservationCompleteEvent event) {
		System.out.println("Okinuta rezervacija");
		this.event = event;
		Reservation r = event.getReservation();
		List<Ticket> tickets = (List<Ticket>) r.getTicketList();
		
		if(r.getTicketList().size() == event.getInvitedUsers().size() && event.getInvitedUsers().size() > 0) {
			for(int i=0; i<event.getInvitedUsers().size(); i++) {
				sendInvitations(event.getInvitedUsers().get(i), tickets.get(i));
			}
		}
		confirmReservation();
	}
	
	@SuppressWarnings("unused")
	private void confirmReservation() {
		Reservation r = event.getReservation();
		
		for(Ticket t : r.getTicketList()) {
			Projection proj = t.getProjection();
			Hall h = t.getSeating().getHallSegment().getHall();
			
			String time = t.getTime().toString();
			String projName = proj.getName();
			Integer duration = proj.getDurationMinutes();
			String auditName = h.getName();
			
			String text = "You successfully created a reservation for " + projName + ".";
			text += "\\n\\nDuration: " + duration;
			text+= "\\n\\nHall: " + auditName;
			text+="\\n\\nTime: " + time;
			text += "\\n\\nLocation: " + auditName;
			text += "\\n\\nPrice: " + event.getReservation().getPrice();
			String subject = "Reservation info";
			
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setSubject(subject);
			msg.setText(text);
			msg.setTo(r.getReservedBy().getEmail());
			
			mailSender.send(msg);
		}
		
	}
	
	private void sendInvitations(User u, Ticket t) {
		Reservation r = event.getReservation();
		
		String reservedBy = r.getReservedBy().getUsername();
		String linkToAcceptance = "localhost:4200/#/invitations/" + t.getId();
		String projName = t.getProjection().getName();
		Integer duration = t.getProjection().getDurationMinutes();
		String hallName = t.getSeating().getHallSegment().getHall().getName();
		String auditName = t.getSeating().getHallSegment().getHall().getAuditorium().getName();
		
		String text = "You are invited to join " + reservedBy + " in watching " + projName + ".";
		text += "\\n\\nDuration: " + duration;
		text+= "\\n\\nHall: " + hallName;
		text+="\\n\\nTime: " + duration;
		text += "\\n\\nLocation: " + auditName;
		text += "\\n\\nClick this link to accept or decline: \\n\\n " + linkToAcceptance;
		String subject = "Invitation from " + reservedBy;
		
		Invitation inv = new Invitation();
		inv.setInvitedUser(u);
		inv.setReservation(event.getReservation());
		invDao.insert(inv);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject(subject);
		msg.setText(text);
		msg.setTo(u.getEmail());
		
		mailSender.send(msg);
	}
}