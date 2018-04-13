package com.msmisa.TicketApp.resources;


import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Hall;
import com.msmisa.TicketApp.dao.hall.HallDao;
import com.msmisa.TicketApp.dto.preview.CinemaPreviewDTO;
import com.msmisa.TicketApp.dto.preview.HallPreviewDTO;

@RestController
@RequestMapping(value="/hall")
public class HallResource extends AbstractController<Hall, Integer> {

	@GetMapping(value="/byauditorim/{id}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getByAuditoriumID(@PathParam("id") Integer id) {
		HallDao hallDao = (HallDao) getDao();
		List<Hall> hallList = hallDao.getByAuditoriumId(id);
		
		return null;
	}
}
