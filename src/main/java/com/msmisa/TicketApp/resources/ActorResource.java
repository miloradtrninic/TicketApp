package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.msmisa.TicketApp.beans.movie.Actor;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.ActorPreviewDTO;

public class ActorResource extends AbstractController<Actor, Integer> {

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ActorPreviewDTO>> getAll(){
		List<Actor> list = getDao().getAll();
		List<ActorPreviewDTO> actorDtoList = convertToDto(list, ActorPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<ActorPreviewDTO>>(actorDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<ActorPreviewDTO>>(actorDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ActorPreviewDTO addNew(@DTO(value=ActorPreviewDTO.class) Actor actor) {
		return convertToDto(getDao().insert(actor), ActorPreviewDTO.class);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ActorPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), ActorPreviewDTO.class);
	}
}
