package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.beans.HallSegment;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Seating;
import com.msmisa.TicketApp.beans.Termin;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.projection.ProjectionDao;
import com.msmisa.TicketApp.dao.projection.TerminDao;
import com.msmisa.TicketApp.dao.ticket.TicketDao;
import com.msmisa.TicketApp.dto.preview.TicketPreviewDTO;

@RestController
@RequestMapping(value="/ticket")
public class TicketResource extends AbstractController<Ticket, Integer>{
	
	@Autowired
	private ProjectionDao projDao;
	
	@Autowired
	private TicketDao tickDao;
	
	@Autowired
	private TerminDao terminDao;
	
	@GetMapping(value="getAvailableTickets/{id}", produces= {"application/json"})
	public ResponseEntity<?> getFreeTicketsForProjection(@PathVariable("id") Integer projId) {
		try {
			Projection p = projDao.get(projId);
			
			List<TicketPreviewDTO> tickets = p.getTicketList().stream()
												.filter(t -> t.getSeating().getReserved() == false)
												.map(t -> convertToDto(t, TicketPreviewDTO.class))
												.collect(Collectors.toList());
			return new ResponseEntity<List<TicketPreviewDTO>>(tickets, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error getting available tickets", HttpStatus.NO_CONTENT);
		}
	}
	
	/*Id = terminId*/
	@GetMapping(value="/createTickets/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTickets(@PathVariable("id") Integer id) {
		
		List<TicketPreviewDTO> ticks = new ArrayList<TicketPreviewDTO>();
		
		try {
			Termin term = terminDao.get(id);
			Set<Hall> halls = term.getHallList();
			
			for(Hall h : halls) {
				for(HallSegment seg : h.getHallSegmentList()) {
					for(Seating seat : seg.getSeatingList()) {
						Ticket t = new Ticket();
						t.setPrice(term.getPrice().doubleValue());
						t.setSeating(seat);
						t.setProjection(term.getProjection());
						t.setTime(term.getDate());
						t.setDiscount(term.getDiscount() ? 1 : 0);
						t.setQuickReservation(false);
						ticks.add(modelMapper.map(tickDao.insert(t), TicketPreviewDTO.class));
					}
				}
			}
			
			return new ResponseEntity<List<TicketPreviewDTO>>(ticks, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error making tickets.", HttpStatus.NO_CONTENT);
		}
	}
	
	/*Id = terminId*/
	@GetMapping(value="/updateTickets/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTickets(@PathVariable("id") Integer id) {
		List<TicketPreviewDTO> ticks = new ArrayList<TicketPreviewDTO>();
		
		try {
			Termin term = terminDao.get(id);
			Projection p = term.getProjection();
			Set<Hall> halls = term.getHallList();
			List<Ticket> tickets = tickDao.getTicketsForProjection(p.getId());
			
			for(Ticket t : tickets) {
				for(Hall h : halls) {
					for(HallSegment seg : h.getHallSegmentList()) {
						for(Seating seat : seg.getSeatingList()) {
							t.setPrice(term.getPrice().doubleValue());
							t.setSeating(seat);
							t.setProjection(term.getProjection());
							t.setTime(term.getDate());
							ticks.add(modelMapper.map(tickDao.update(t), TicketPreviewDTO.class));
						}
					}
				}
			}
			
			return new ResponseEntity<List<TicketPreviewDTO>>(ticks, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error making tickets.", HttpStatus.NO_CONTENT);
		}
	}
}
