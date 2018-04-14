package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.movie.Director;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.DirectorPreviewDTO;

@RestController
@RequestMapping(value="/director")
public class DirectorResource extends AbstractController<Director, Integer> {

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DirectorPreviewDTO>> getAll(){
		List<Director> list = getDao().getAll();
		List<DirectorPreviewDTO> directorDtoList = convertToDto(list, DirectorPreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<DirectorPreviewDTO>>(directorDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<DirectorPreviewDTO>>(directorDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public DirectorPreviewDTO addNew(@DTO(value=DirectorPreviewDTO.class) Director director) {
		return convertToDto(getDao().insert(director), DirectorPreviewDTO.class);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public DirectorPreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), DirectorPreviewDTO.class);
	}
}
