package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.auditorium.AuditoriumDao;
import com.msmisa.TicketApp.dao.ticket.ReservationDao;
import com.msmisa.TicketApp.dao.ticket.TicketDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.ReservationCreationDTO;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TicketPreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;

@RestController
@RequestMapping(value="/reservation")
public class ReservationResource extends AbstractController<Reservation, Integer>{
	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private TicketDao tickDao;

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
	public ResponseEntity<?> updateInfo(@RequestBody ReservationPreviewDTO update) {
		ResponseEntity<?> ret = null;

		try {
			Reservation r = reservationDao.get(update.getId());
			r.setReservedBy(r.getReservedBy());
			List<Integer> keys = new ArrayList<Integer>();
			
			keys = r.getTicketList().stream().map(t -> t.getId()).collect(Collectors.toList());
			r.setTicketList((Set<Ticket>) tickDao.getAllIn(keys));

			Reservation toRet = reservationDao.update(r);

			ret = new ResponseEntity<ReservationPreviewDTO>(convertToDto(toRet, ReservationPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("Ne moze update..");
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error updating Reservation info.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ret;
	}
	
	@PostMapping(value="/new", consumes= {"application/json"}, produces= {"application/json"})
	public ResponseEntity<?> createReservation(@DTO(ReservationCreationDTO.class) Reservation res) {
		@SuppressWarnings("unchecked")
		List<Ticket> resTicketList = (List<Ticket>) res.getTicketList();
		List<Ticket> allTickets = tickDao.getAll();
		ResponseEntity<?> ret = null;
		try {
			for(Ticket t : allTickets) {
				for(Ticket ut : resTicketList) {
					if(ut.getId().equals(t.getId()) && !t.getVersion().equals(ut.getVersion())) {
						ret = new ResponseEntity<String>("Try again with different selections.", HttpStatus.CONFLICT);
					}
				}
			}
			if(ret == null) {
				res.setTicketList(resTicketList.stream().map(t -> t = tickDao.update(t)).collect(Collectors.toSet()));
				ret = new ResponseEntity<Reservation>(res, HttpStatus.OK);
			}
		} catch(Exception e) {
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error making reservation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ret;
	}
	
	@Override
	@RequestMapping(value="/delete/{id}",
			method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value="id") Integer id){
		Reservation r = reservationDao.get(id);
		Date cancelationTime = new Date();
		Ticket t = r.getTicketList().stream().findFirst().get();
		Date projectionTime = t != null ? projectionTime = t.getTime() : null;
		
		if(projectionTime != null) {
			if(canCancel(cancelationTime, projectionTime)) {
				reservationDao.delete(r.getId());
				return new ResponseEntity<String>("Success canceling reservation", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Error. Reservations can be canceled up to 30 minutes before the begining.", HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<String>("Error canceling reservation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean canCancel(Date cancelationTime, Date projectionTime) {
		int diff = (int)((projectionTime.getTime()/60000) - (cancelationTime.getTime()/60000));
		
		return diff >= 30;
	}
}
