package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.ticket.ReservationDao;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.dto.update.AuditoriumAdminUpdateDTO;

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
	
	@GetMapping(value="admin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuditoriumPreviewDTO>> getAdminAuds(){
		/* TODO Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Auditorium> list = ((AuditoriumDao) getDao()).getByAdmin(authentication.getName());*/
		List<Auditorium> list = ((AuditoriumDao) getDao()).getAll();
		List<AuditoriumPreviewDTO> audDtoList = convertToDto(list, AuditoriumPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<AuditoriumPreviewDTO>>(audDtoList,HttpStatus.OK);
	}
	@GetMapping(value="/{id}/admins", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserPreviewDTO>> getAuditoriumAdmins(@PathVariable("id") Integer id){
		AuditoriumDao dao = (AuditoriumDao) getDao();
		Set<User> admins = dao.getAdmins(id);
		List<UserPreviewDTO> adminsPreview = admins.stream().map(a -> modelMapper.map(a, UserPreviewDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<List<UserPreviewDTO>>(adminsPreview,HttpStatus.OK);
	}

	@PutMapping(value="update/admins", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuditoriumPreviewDTO> updateAdmins(@RequestBody AuditoriumAdminUpdateDTO update){
		AuditoriumDao dao = (AuditoriumDao) getDao();
		Auditorium aud = dao.get(update.getId());
		List<User> admins = userDao.getAllIn(update.getAdminFKs());
		aud.setAdmin(new HashSet<>(admins));
		dao.update(aud);
		return new ResponseEntity<AuditoriumPreviewDTO>(convertToDto(aud, AuditoriumPreviewDTO.class), HttpStatus.OK);
	}


}
