package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.hall.SeatingDao;
import com.msmisa.TicketApp.dao.ticket.ReservationDao;
import com.msmisa.TicketApp.dao.ticket.TicketDao;
import com.msmisa.TicketApp.dao.user.InvitationDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.ReservationCreationDTO;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TicketPreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.events.OnReservationCompleteEvent;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;

@RestController
@RequestMapping(value="/reservation")
public class ReservationResource extends AbstractController<Reservation, Integer>{
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private TicketDao tickDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SeatingDao seatDao;
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private InvitationDao invDao;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@GetMapping(value={"getaAll/{id}"}, produces= {"application/json"})
	public ResponseEntity<?> getAllForUser(@RequestParam("id") String id) {
		List<Reservation> reservations = reservationDao.getAllForUser(Integer.parseInt(id));
		List<ReservationPreviewDTO> resPreview = convertToDto(reservations, ReservationPreviewDTO.class);
		if(reservations.isEmpty())
			return new ResponseEntity<List<ReservationPreviewDTO>>(resPreview, HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<ReservationPreviewDTO>>(resPreview, HttpStatus.OK);
	}
	
	@GetMapping(value= {"/{id}"}, produces= {"application/json"})
	public ReservationPreviewDTO get(@RequestParam("id") String id) {
		return convertToDto(reservationDao.get(Integer.parseInt(id)), ReservationPreviewDTO.class);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<List<ReservationPreviewDTO>> ret = null;
		List<Reservation> res = reservationDao.getAll();
		List<ReservationPreviewDTO> reservationsDTO = res.stream().map(r -> convertToDto(r, ReservationPreviewDTO.class)).collect(Collectors.toList());

		if(reservationsDTO.isEmpty())
			ret = new ResponseEntity<List<ReservationPreviewDTO>>(reservationsDTO, HttpStatus.NO_CONTENT);
		else
			ret = new ResponseEntity<List<ReservationPreviewDTO>>(reservationsDTO, HttpStatus.OK);

		return ret;
	}
	
	@PutMapping(value="/update", consumes= {"application/json"}, produces= {"application/json"})
	public ResponseEntity<?> updateInfo(@RequestBody ReservationCreationDTO update) {
		ResponseEntity<?> ret = null;

		try {
			Reservation r = reservationDao.get(update.getId());
			List<Integer> keys = update.getTicketList();
			
			r.setTicketList(new HashSet<Ticket>(tickDao.getAllIn(keys)));

			Reservation toRet = reservationDao.update(r);

			ret = new ResponseEntity<ReservationPreviewDTO>(convertToDto(toRet, ReservationPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("Ne moze update..");
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error updating Reservation info.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ret;
	}
	
	@PostMapping(value="/new", consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createReservation(@RequestBody ReservationCreationDTO res, WebRequest req) {
		ResponseEntity<?> ret = null;
		
		try {
			List<Ticket> resTicketList = new ArrayList<Ticket>();
			List<User> invitedUsers = new ArrayList<User>();
			for(Integer key : res.getInvitedUsersID()) {
				User u = userDao.get(key);
				invitedUsers.add(u);
			}
			for(Integer key : res.getTicketList()) {
				Ticket t = tickDao.get(key);
				resTicketList.add(t);
			}
			
			
			Reservation r = new Reservation();
			r.setReservedBy(userDao.get(res.getReservedBy()));
			r.setTicketList(new HashSet<Ticket>(resTicketList));
			r.setTicketList(new HashSet<Ticket>(resTicketList));
			ret = new ResponseEntity<ReservationPreviewDTO>(convertToDto(r, ReservationPreviewDTO.class), HttpStatus.OK);
			
			confirmReservation(r, res.getPrice().intValue());
			
			int i = 0;
			for(User u : invitedUsers) {
				sendInvitations(u, resTicketList.get(i++), r);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error making reservation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ret;
	}
	
	private void confirmReservation(Reservation r, int price) {

		
		for(Ticket t : r.getTicketList()) {
			Projection proj = t.getProjection();
			Hall h = t.getSeating().getHallSegment().getHall();
			
			String time = t.getTime().toString();
			String projName = proj.getName();
			Integer duration = proj.getDurationMinutes();
			String auditName = h.getName();
			
			String text = "You successfully created a reservation for " + projName + ".";
			text += "\n\nDuration: " + duration;
			text+= "\n\nHall: " + auditName;
			text+="\n\nTime: " + time;
			text += "\n\nLocation: " + auditName;
			text += "\n\nPrice: " + price;
			String subject = "Reservation info";
			
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setSubject(subject);
			msg.setText(text);
			msg.setTo(r.getReservedBy().getEmail());
			
			mailSender.send(msg);
		}
		
	}
	
	private void sendInvitations(User u, Ticket t, Reservation r) {
		
		String reservedBy = r.getReservedBy().getUsername();
		String linkToAcceptance = "localhost:4200/#/invitations/" + t.getId();
		String projName = t.getProjection().getName();
		Integer duration = t.getProjection().getDurationMinutes();
		String hallName = t.getSeating().getHallSegment().getHall().getName();
		String auditName = t.getSeating().getHallSegment().getHall().getAuditorium().getName();
		
		String text = "You are invited to join " + reservedBy + " in watching " + projName + ".";
		text += "\n\nDuration: " + duration;
		text+= "\n\nHall: " + hallName;
		text+="\n\nTime: " + duration;
		text += "\n\nLocation: " + auditName;
		text += "\n\nClick this link to accept or decline: \n\n " + linkToAcceptance;
		String subject = "Invitation from " + reservedBy;
		
		Invitation inv = new Invitation();
		inv.setInvitedUser(u);
		inv.setReservation(r);
		invDao.insert(inv);
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject(subject);
		msg.setText(text);
		msg.setTo(u.getEmail());
		
		mailSender.send(msg);
	}
	
	@Override
	@RequestMapping(value="/delete/{id}",
			method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value="id") Integer id){
		Reservation r = reservationDao.get(id);
		Date cancelationTime = new Date();
		Ticket t = r.getTicketList().stream().findFirst().get();
		Date projectionTime = t != null ? projectionTime = t.getTime() : null;
		
		try {
			if(projectionTime != null) {
				if(canCancel(cancelationTime, projectionTime)) {
					for(Ticket ticket : r.getTicketList()) {
						ticket.getSeating().setReserved(false);
						seatDao.update(t.getSeating());
						reservationDao.delete(r.getId());
						tickDao.delete(t.getId());
					}
					return new ResponseEntity<String>("Success canceling reservation", HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Error. Reservations can be canceled up to 30 minutes before the begining.", HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				return new ResponseEntity<String>("Error canceling reservation", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error canceling reservation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean canCancel(Date cancelationTime, Date projectionTime) {
		int diff = (int)((projectionTime.getTime()/60000) - (cancelationTime.getTime()/60000));
		
		return diff >= 30;
	}
}
