package com.msmisa.TicketApp.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Cinema;
import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.dao.auditorium.CinemaDao;
import com.msmisa.TicketApp.dao.projection.MovieDao;
import com.msmisa.TicketApp.dao.projection.ProjectionDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.MovieCreationDTO;
import com.msmisa.TicketApp.dto.preview.CinemaPreviewDTO;
import com.msmisa.TicketApp.dto.preview.MoviePreviewDTO;
import com.msmisa.TicketApp.dto.update.CinemaUpdateDTO;
import com.msmisa.TicketApp.dto.update.MovieUpdateDTO;
import com.msmisa.TicketApp.dto.update.ProjectionRateUpdateDTO;

@RestController
@RequestMapping(value="/movie")
public class MovieResource extends AbstractController<Movie, Integer>{

	@Autowired
	CinemaDao cinemaDao;
	
	@Autowired
	ProjectionDao projectionDao;
	
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
		movie.setId(null);
		movie.getActors().forEach(act -> logger.info(act.getId() + act.getName() + act.getLastName()));
		logger.info("Director " + movie.getDirector().getName());
		movie.getGenres().forEach(gen -> logger.info(gen.getId() + gen.getName()));
		MovieDao movieDao = (MovieDao) getDao();
		return convertToDto(movieDao.insert(movie), MoviePreviewDTO.class);
	}
	
	@GetMapping(value="allFromCinema/{cinemaId}")
	public ResponseEntity<?> allFromCinema(@PathVariable("cinemaId") Integer cinemaId) {
		
		return new ResponseEntity<List<MoviePreviewDTO>>
				(convertToDto(cinemaDao.getAllMovies(cinemaId), MoviePreviewDTO.class), HttpStatus.OK);
	}
	
	@GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MoviePreviewDTO getId (@PathVariable(value="id") Integer id) {
		return convertToDto(getDao().get(id), MoviePreviewDTO.class);
	}
	
	
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MoviePreviewDTO update(@RequestBody MovieUpdateDTO entity){
		Movie movie = getDao().get(entity.getId());
		movie.setName(entity.getName());
		movie.setDescription(entity.getDescription());
		movie.setDurationMinutes(entity.getDurationMinutes());
		movie.setCoverPath(entity.getCoverPath());
		
		return convertToDto(getDao().update(movie), MoviePreviewDTO.class);
	
	}
	
	@PostMapping(value="rate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectionRateUpdateDTO> update(@RequestBody ProjectionRateUpdateDTO rate){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		projectionDao.rateProjection(authentication.getName(), rate.getProjectionId(), rate.getRating());
		return new ResponseEntity<ProjectionRateUpdateDTO>(rate, HttpStatus.OK);
	}
	
}
