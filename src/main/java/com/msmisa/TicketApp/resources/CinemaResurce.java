package com.msmisa.TicketApp.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.CinemaCreationDTO;
import com.msmisa.TicketApp.dto.preview.CinemaPreviewDTO;
import com.mysql.fabric.xmlrpc.base.Array;

@RestController
@RequestMapping(value="/cinema")
public class CinemaResurce extends AbstractController<Cinema, Integer>{
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CinemaPreviewDTO>> getAll(){
		List<Cinema> list = getDao().getAll();
		List<CinemaPreviewDTO> cinemaDtoList = convertToDto(list, CinemaPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<CinemaPreviewDTO>>(cinemaDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<CinemaPreviewDTO>>(cinemaDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public CinemaPreviewDTO addNew(@DTO(value=CinemaCreationDTO.class) Cinema cinema) {
		return convertToDto(getDao().insert(cinema), CinemaPreviewDTO.class);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CinemaPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), CinemaPreviewDTO.class);
	}
	
}

