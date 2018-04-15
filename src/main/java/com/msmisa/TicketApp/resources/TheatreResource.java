package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.TheatreCreationDTO;
import com.msmisa.TicketApp.dto.preview.TheatrePreviewDTO;
import com.msmisa.TicketApp.dto.update.TheatreUpdateDTO;

@RestController
@RequestMapping(value="/theatre")
public class TheatreResource extends AbstractController<Theatre, Integer> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TheatrePreviewDTO>> getAll(){
		List<Theatre> list = getDao().getAll();
		List<TheatrePreviewDTO> theatreDtoList = list.stream().map(c -> convertToDto(c, TheatrePreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<TheatrePreviewDTO>>(theatreDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<TheatrePreviewDTO>>(theatreDtoList,HttpStatus.OK);

	}
	
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public TheatrePreviewDTO addNew(@DTO(value=TheatreCreationDTO.class) Theatre theatre) {
		return convertToDto(getDao().insert(theatre), TheatrePreviewDTO.class);
	}
	
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TheatrePreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), TheatrePreviewDTO.class);
	}
	
	
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Theatre update(@DTO(value=TheatreUpdateDTO.class) Theatre entity){
		return getDao().update(entity);
	}
	
}
