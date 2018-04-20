package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Ticket;
import com.msmisa.TicketApp.dao.projection.ProjectionDao;
import com.msmisa.TicketApp.dto.preview.TicketPreviewDTO;

@RestController
@RequestMapping(value="/ticket")
public class TicketResource extends AbstractController<Ticket, Integer>{
	
	@Autowired
	private ProjectionDao projDao;
	
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
}
