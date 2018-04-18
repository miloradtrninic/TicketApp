package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.ticket.ReservationDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;

@RestController
@RequestMapping(value="/auditorium")
public class AuditoriumResource extends AbstractController<Auditorium, Integer>  {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuditoriumDao auditoriumDao;
	
	@Autowired
	private ReservationDao reservationDao;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuditoriumPreviewDTO>> getAll(){
		List<Auditorium> list = getDao().getAll();
		List<AuditoriumPreviewDTO> audDtoList = convertToDto(list, AuditoriumPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.OK);

	}
	
	@RequestMapping(value="/getVisits/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVisits(@PathVariable("id") Integer id) {
		final long minuteInMs = 60000;
		List<Reservation> reservations = reservationDao.getAllForUser(id);
		List<AuditoriumPreviewDTO> retEnt = new ArrayList<AuditoriumPreviewDTO>();
		Date now = new Date();
		ResponseEntity<?> retResp = null;
		
		try {
			for(Reservation r : reservations) {
				@SuppressWarnings("unchecked")
				List<Ticket> tickets = (List<Ticket>) r.getTicketList();
				Ticket t = tickets.get(0);
				Integer durationMinutes = t.getProjection().getDurationMinutes();
				Date projectionTime = t.getTime();
				long projectionTimeMs = projectionTime.getTime();
				Date projectionEndTime = new Date(projectionTimeMs + (durationMinutes * minuteInMs));
				
				if(now.after(projectionEndTime)) {
					Auditorium a = t.getSeating().getHallSegment().getHall().getAuditorium();
					retEnt.add(convertToDto(a, AuditoriumPreviewDTO.class));
				}
			}
			
			retResp = new ResponseEntity<List<AuditoriumPreviewDTO>>(retEnt, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			retResp = new ResponseEntity<String>("Error getting visits.", HttpStatus.NO_CONTENT);
		}
		
		return retResp;
	}
}
