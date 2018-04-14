package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.MovieCreationDTO;
import com.msmisa.TicketApp.dto.preview.MoviePreviewDTO;

@RestController
@RequestMapping(value="/movie")
public class MovieResource extends AbstractController<Movie, Integer>{

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MoviePreviewDTO>> getAll(){
		List<Movie> list = getDao().getAll();
		List<MoviePreviewDTO> movieDtoList = list.stream().map(c -> convertToDto(c, MoviePreviewDTO.class)).collect(Collectors.toList());
		if(list.isEmpty())
			return new ResponseEntity<List<MoviePreviewDTO>>(movieDtoList,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<MoviePreviewDTO>>(movieDtoList,HttpStatus.OK);

	}
	
	
	@PostMapping(value="/new",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public MoviePreviewDTO addNew(@DTO(value=MovieCreationDTO.class) Movie movie) {
		return convertToDto(getDao().insert(movie), MoviePreviewDTO.class);
	}
	
	
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MoviePreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), MoviePreviewDTO.class);
	}
	
	
}
