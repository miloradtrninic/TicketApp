package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Invitation;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.ticket.TicketDao;
import com.msmisa.TicketApp.dao.user.InvitationDao;
import com.msmisa.TicketApp.dto.preview.InvitationPreviewDTO;

@RestController
@RequestMapping(value="/invitation")
public class InivitationResource extends AbstractController<Invitation, Integer> {
	
	@Autowired
	private InvitationDao invDao;
	
	@Autowired
	private TicketDao tickDao;
	
	@GetMapping(value="/getForUser/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<InvitationPreviewDTO> getInvitationsForUser(@PathVariable("id") Integer id) {
		return convertToDto(invDao.getInvitationsForUser(id), InvitationPreviewDTO.class);
	}
	
	@PutMapping(value="/accept/{id}")
	public ResponseEntity<?> acceptInvitation(@PathVariable("id") Integer id) {
		try {
			invDao.delete(id);
			return new ResponseEntity<String>("Successfully accepted invitation!", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error accepting invitation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/decline/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> declineInvitation(@PathVariable("id") Integer id) {
		Invitation inv = invDao.get(id);
		
		try {
			Reservation r = inv.getReservation();
			Set<Ticket> tickets = r.getTicketList();
			Ticket tick = tickets.stream().filter(t -> t.getSeating().getReserved() == true).findFirst().get();
			
			tick.getSeating().setReserved(false);
			tickDao.update(tick);
			invDao.delete(id);
			
			return new ResponseEntity<String>("Successfully accepted invitation!", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error accepting invitation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
