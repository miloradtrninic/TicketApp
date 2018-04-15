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

import com.msmisa.TicketApp.beans.movie.Genre;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.preview.GenrePreviewDTO;

@RestController
@RequestMapping(value="/genre")
public class GenreResource extends AbstractController<Genre, Integer> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GenrePreviewDTO>> getAll(){
		List<Genre> list = getDao().getAll();
		List<GenrePreviewDTO> genreDtoList = convertToDto(list, GenrePreviewDTO.class);
		if(list.isEmpty())
			return new ResponseEntity<List<GenrePreviewDTO>>(genreDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<GenrePreviewDTO>>(genreDtoList,HttpStatus.OK);

	}
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public GenrePreviewDTO addNew(@DTO(value=GenrePreviewDTO.class) Genre genre) {
		return convertToDto(getDao().insert(genre), GenrePreviewDTO.class);
	}
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenrePreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), GenrePreviewDTO.class);
	}
}
