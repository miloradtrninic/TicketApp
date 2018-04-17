package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.beans.Reservation;
import com.msmisa.TicketApp.dao.ticket.ReservationDao;
import com.msmisa.TicketApp.dao.ticket.TicketDao;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;
import com.msmisa.TicketApp.dto.preview.TicketPreviewDTO;
import com.msmisa.TicketApp.dto.preview.ReservationPreviewDTO;

@RestController
@RequestMapping(value="/reservation")
public class ReservationResource extends AbstractController<Reservation, Integer>{
	@Autowired
	private ReservationDao dao;
	
	@Autowired
	private TicketDao tickDao;
	


	@GetMapping(value={"getaAll/{id}"}, produces= {"application/json"})
	public ResponseEntity<?> getAllIn(@RequestParam("id") String id) {
		int resid = Integer.parseInt(id);
		List<Reservation> reservations = getDao().getAllIn(new ArrayList<Integer>(Arrays.asList(resid)));
		List<ReservationPreviewDTO> resPreview = convertToDto(reservations, ReservationPreviewDTO.class);
		if(reservations.isEmpty())
			return new ResponseEntity<List<ReservationPreviewDTO>>(resPreview, HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<ReservationPreviewDTO>>(resPreview, HttpStatus.OK);
	}
	
	@GetMapping(value= {"/{id}"}, produces= {"application/json"})
	public ReservationPreviewDTO get(@RequestParam("id") String id) {
		return convertToDto(dao.get(Integer.parseInt(id)), ReservationPreviewDTO.class);
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<List<ReservationPreviewDTO>> ret = null;
		List<Reservation> res = dao.getAll();
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
			Reservation r = dao.get(update.getId());
			r.setReservedBy(r.getReservedBy());
			List<Integer> keys = new ArrayList<Integer>();
			
			keys = r.getTicketList().stream().map(t -> t.getId()).collect(Collectors.toList());
			r.setTicketList((Set<Ticket>) tickDao.getAllIn(keys));

			Reservation toRet = dao.update(r);

			ret = new ResponseEntity<ReservationPreviewDTO>(convertToDto(toRet, ReservationPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("Ne moze update..");
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error updating Reservation info.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ret;
	}
	
	
}
